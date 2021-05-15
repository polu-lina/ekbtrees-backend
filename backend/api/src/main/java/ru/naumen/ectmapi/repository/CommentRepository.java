package ru.naumen.ectmapi.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import ru.naumen.ectmapi.entity.Comment;

@Mapper
public interface CommentRepository {

    void create(@Param("comment") Comment comment);

    Comment find(@Param("id") Long id);

    void delete(@Param("id") Long id);
}
