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

    @Column(name = "treeHeight")
    private Double treeHeight;

    @Column(name = "numberOfTreeTrunks")
    private int numberOfTreeTrunks;

    @Column(name = "trunkGirth")
    private Double trunkGirth;

    @Column(name = "diameterOfCrown")
    private Double diameterOfCrown;

    @Column(name = "heightOfTheFirstBranch")
    private Double heightOfTheFirstBranch;

    @Column(name = "conditionAssessment")
    private int conditionAssessment;

    @Column(name = "age")
    private int age;

    @Column(name = "treePlantingType")
    private String treePlantingType;

    @Column(name = "created")
    private Instant created;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "authorId")
    private Long authorId;

    @Column(name = "status")
    private String status;
}
