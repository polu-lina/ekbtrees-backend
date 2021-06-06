package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.postgis.Point;
import ru.naumen.ectmapi.entity.TreeMapInfo;

import java.util.List;

@Mapper
public interface TreeMapInfoRepository {

    List<TreeMapInfo> findInRegion(@Param("topLeft") Point topLeft, @Param("bottomRight") Point bottomRight);

    List<TreeMapInfo> findAllByAuthorId(@Param("authorId") Long authorId);
}
