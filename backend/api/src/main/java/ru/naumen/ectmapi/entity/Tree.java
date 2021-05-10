package ru.naumen.ectmapi.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "tree")
@Data
public class Tree {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "latitude")
    @NotNull
    @Min(-90)
    @Max(90)
    private Double latitude;

    @Column(name = "longitude")
    @NotNull
    @Min(-180)
    @Max(180)
    private Double longitude;

    @Column(name = "type")
    private String type;

    @Column(name = "tree_height")
    private Double treeHeight;

    @Column(name = "number_of_tree_trunks")
    private int numberOfTreeTrunks;

    @Column(name = "trunk_girth")
    private Double trunkGirth;

    @Column(name = "diameter_of_crown")
    private Double diameterOfCrown;

    @Column(name = "height_of_the_first_branch")
    private Double heightOfTheFirstBranch;

    @Column(name = "condition_assessment")
    private int conditionAssessment;

    @Column(name = "age")
    private int age;

    @Column(name = "tree_plantingType")
    private String treePlantingType;

    @Column(name = "created")
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "status")
    private String status;
}
