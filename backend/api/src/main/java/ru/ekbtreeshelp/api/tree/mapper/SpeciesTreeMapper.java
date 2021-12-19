package ru.ekbtreeshelp.api.tree.mapper;

import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.tree.dto.SpeciesTreeDto;
import ru.ekbtreeshelp.core.entity.SpeciesTree;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SpeciesTreeMapper {

    List<SpeciesTreeDto> toDto(List<SpeciesTree> speciesTrees);

    SpeciesTree fromDto(SpeciesTreeDto speciesTreeDto);

    default SpeciesTree fromLong(Long speciesId) {
        SpeciesTree speciesTree = new SpeciesTree();
        speciesTree.setId(speciesId);
        return speciesTree;
    }

    SpeciesTreeDto toDto(SpeciesTree speciesTree);
}
