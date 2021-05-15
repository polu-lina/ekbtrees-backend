package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.postgis.Point;
import ru.naumen.ectmapi.entity.TreesCluster;

import java.util.List;

@Mapper
public interface TreesClusterRepository {

    List<TreesCluster> findInRegion(@Param("topLeft") Point topLeft, @Param("bottomRight") Point bottomRight);
}
