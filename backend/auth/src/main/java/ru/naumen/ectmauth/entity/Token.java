package ru.naumen.ectmauth.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name="tokens")
public class Token {



    @Id
    @Column(name="token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    @CreationTimestamp
    @Schema(description = "Дата создания токенов")
    private Date created;

    @ManyToOne()
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "refresh_token пользователя для приложения EkbTreeMap")
    private String refresh_token;
    @Schema(description = "access_token пользователя для приложения EkbTreeMap")
    private String access_token;
    @Schema(description = "access_token пользователя для Вконтакте")
    private String access_token_VK;
    @Schema(description = "access_token пользователя для Facebook")
    private String access_token_FB;



    public Long getToken_id() {
        return token_id;
    }

    public void setToken_id(Long token_id) {
        this.token_id = token_id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(Optional<User> user) {
        this.user = user.get();
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token_VK() {
        return access_token_VK;
    }

    public void setAccess_token_VK(String access_token_VK) {
        this.access_token_VK = access_token_VK;
    }

    public String getAccess_token_FB() { return access_token_FB; }

    public void setAccess_token_FB(String access_token_FB) { this.access_token_FB = access_token_FB; }


}
