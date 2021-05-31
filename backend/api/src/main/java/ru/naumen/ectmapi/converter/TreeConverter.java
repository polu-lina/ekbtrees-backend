package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.TreeDto;
import ru.naumen.ectmapi.entity.Tree;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        PointConverter.class,
        SpeciesTreeConverter.class
})
public interface TreeConverter {

    TreeDto toDto(Tree tree);

    List<TreeDto> toDto(List<Tree> trees);

    Tree fromDto(TreeDto treeDto);
}
