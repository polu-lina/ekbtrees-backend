package ru.ekbtreeshelp.api.tree.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ekbtreeshelp.api.tree.dto.TreeMapInfoDto;
import ru.ekbtreeshelp.core.entity.Tree;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PointMapper.class,
        SpeciesTreeMapper.class
})
public interface TreeMapInfoMapper {

    @Mapping(source = "geoPoint", target = "geographicalPoint")
    TreeMapInfoDto toDto(Tree tree);

    List<TreeMapInfoDto> toDto(List<Tree> treesMapInfo);
}
