package ru.naumen.ectmauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "refresh_tokens")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
public class RefreshToken extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String token;

}
