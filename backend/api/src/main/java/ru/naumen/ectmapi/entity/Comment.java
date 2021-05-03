package ru.naumen.ectmapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Entity(name = "comment")
@Data
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "created_comment")
    private Instant created;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "text")
    private String text;

    @Column(name = "tree_id")
    private Long treeId;
}
