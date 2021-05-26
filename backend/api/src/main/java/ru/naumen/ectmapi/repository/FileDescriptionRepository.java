package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.naumen.ectmapi.entity.FileDescription;

import java.util.List;

@Mapper
public interface FileDescriptionRepository {

    void create(@Param("fileDescription") FileDescription fileDescription);

    FileDescription find(@Param("id") Long id);

    FileDescription findFirstByHash(@Param("hash") String hash);

    List<FileDescription> findAllByTreeId(@Param("treeId") Long treeId);

    void updateTreeId(@Param("id") Long id, @Param("treeId") Long treeId);

    long countByHash(@Param("hash") String hash);

    void delete(@Param("id") Long id);
}
