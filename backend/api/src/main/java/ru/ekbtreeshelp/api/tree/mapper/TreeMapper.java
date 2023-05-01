package ru.ekbtreeshelp.api.tree.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.ekbtreeshelp.api.security.permissions.constants.Roles;
import ru.ekbtreeshelp.api.util.mapper.DatesMapper;
import ru.ekbtreeshelp.api.tree.dto.CreateTreeDto;
import ru.ekbtreeshelp.api.tree.dto.TreeDto;
import ru.ekbtreeshelp.api.tree.dto.UpdateTreeDto;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;
import ru.ekbtreeshelp.api.security.permissions.evaluators.MainPermissionEvaluator;
import ru.ekbtreeshelp.api.file.service.FileService;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.core.entity.FileEntity;
import ru.ekbtreeshelp.core.entity.Tree;

import java.util.Arrays;
import java.util.stream.Collectors;

import static ru.ekbtreeshelp.api.security.permissions.constants.Roles.MODERATOR;
import static ru.ekbtreeshelp.api.security.permissions.constants.Roles.SUPERUSER;

@Mapper(
        componentModel = "spring",
        uses = {
                PointMapper.class,
                SpeciesTreeMapper.class,
                DatesMapper.class
        },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class TreeMapper {
    @Autowired
    private MainPermissionEvaluator mainPermissionEvaluator;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private FileService fileService;

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    @Mapping(source = "creationDate", target = "created")
    @Mapping(source = "lastModificationDate", target = "updated")
    @Mapping(target = "trunkStates", ignore = true)
    @Mapping(target = "branchStates", ignore = true)
    @Mapping(target = "corticalStates", ignore = true)
    public abstract TreeDto toDto(Tree tree);

    @AfterMapping
    protected void setRightsFlags(@MappingTarget TreeDto treeDto) {
        var currentUserIsAdminOrModerator = securityService.currentUserHasAnyRole(MODERATOR, SUPERUSER);
        treeDto.setDeletable(currentUserIsAdminOrModerator || mainPermissionEvaluator.hasPermission(
                securityService.getCurrentAuth(), treeDto.getId(), Domains.TREE, Permissions.DELETE));
        treeDto.setEditable(currentUserIsAdminOrModerator || mainPermissionEvaluator.hasPermission(
                securityService.getCurrentAuth(), treeDto.getId(), Domains.TREE, Permissions.EDIT));
    }

    @AfterMapping
    protected void setFileIds(@MappingTarget TreeDto treeDto) {
        treeDto.setFileIds(
                fileService.listByTreeId(treeDto.getId())
                        .stream()
                        .map(FileEntity::getId)
                        .collect(Collectors.toList()));
    }

    @AfterMapping
    protected void setStates(Tree tree, @MappingTarget TreeDto treeDto) {
        if (tree.getBranchStates() != null) {
            treeDto.setBranchStates(Arrays.stream(tree.getBranchStates().split(";")).toList());
        }
        if (tree.getTrunkStates() != null) {
            treeDto.setTrunkStates(Arrays.stream(tree.getTrunkStates().split(";")).toList());
        }
        if (tree.getCorticalStates() != null) {
            treeDto.setCorticalStates(Arrays.stream(tree.getCorticalStates().split(";")).toList());
        }
    }

    @Mapping(source = "geographicalPoint", target = "geoPoint")
    @Mapping(source = "speciesId", target = "species")
    @Mapping(target = "trunkStates", ignore = true)
    @Mapping(target = "branchStates", ignore = true)
    @Mapping(target = "corticalStates", ignore = true)
    public abstract Tree fromDto(CreateTreeDto createTreeDto);

    @AfterMapping
    protected void setStates(CreateTreeDto createTreeDto, @MappingTarget Tree tree) {
        if (createTreeDto.getBranchStates() != null) {
            tree.setBranchStates(String.join(";", createTreeDto.getBranchStates()));
        }
        if (createTreeDto.getCorticalStates() != null) {
            tree.setCorticalStates(String.join(";", createTreeDto.getCorticalStates()));
        }
        if (createTreeDto.getTrunkStates() != null) {
            tree.setTrunkStates(String.join(";", createTreeDto.getTrunkStates()));
        }
    }

    @Mapping(target = "geoPoint", source = "geographicalPoint")
    @Mapping(target = "species", source = "speciesId")
    @Mapping(target = "trunkStates", ignore = true)
    @Mapping(target = "branchStates", ignore = true)
    @Mapping(target = "corticalStates", ignore = true)
    public abstract void updateTreeFromDto(UpdateTreeDto updateTreeDto, @MappingTarget Tree tree);

    @AfterMapping
    protected void setStates(UpdateTreeDto updateTreeDto, @MappingTarget Tree tree) {
        if (updateTreeDto.getBranchStates() != null) {
            tree.setBranchStates(String.join(";", updateTreeDto.getBranchStates()));
        }
        if (updateTreeDto.getCorticalStates() != null) {
            tree.setCorticalStates(String.join(";", updateTreeDto.getCorticalStates()));
        }
        if (updateTreeDto.getTrunkStates() != null) {
            tree.setTrunkStates(String.join(";", updateTreeDto.getTrunkStates()));
        }
    }

    public Tree fromId(Long id) {
        Tree tree = new Tree();
        tree.setId(id);
        return tree;
    }

    public Long toId(Tree tree) {
        return tree != null ? tree.getId() : null;
    }
}
