package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.naumen.ectmapi.entity.Tree;

@Mapper
public interface TreeRepository {

    void create(@Param("tree") Tree tree);

    void update(@Param("tree") Tree tree);

    Tree find(@Param("id") Long id);

    void delete(@Param("id") Long id);

    boolean isExists(@Param("id") Long id);
}
