package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.TreesClusterDto;
import ru.naumen.ectmapi.entity.TreesCluster;

import java.util.List;

@Mapper(componentModel = "spring", uses = PointConverter.class)
public interface TreesClusterConverter {

    List<TreesClusterDto> toDto(List<TreesCluster> treesClusters);
}
