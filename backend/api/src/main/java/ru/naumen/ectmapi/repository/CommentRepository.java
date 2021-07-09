package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.naumen.ectmapi.entity.Comment;

import java.util.List;

@Mapper
public interface CommentRepository {

    void create(@Param("comment") Comment comment);

    void update(@Param("comment") Comment comment);

    Comment find(@Param("id") Long id);

    List<Comment> findAllByTreeId(@Param("treeId") Long treeId);

    void delete(@Param("id") Long id);
}
