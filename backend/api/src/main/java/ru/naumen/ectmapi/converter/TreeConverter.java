package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.TreeDto;
import ru.naumen.ectmapi.entity.Tree;

@Mapper(componentModel = "spring")
public interface TreeConverter {

    TreeDto toDto(Tree tree);

    Tree fromDto(TreeDto treeDto);

}
