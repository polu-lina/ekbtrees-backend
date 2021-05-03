package ru.naumen.ectmauth.token;


import org.hibernate.annotations.CreationTimestamp;
import ru.naumen.ectmauth.user.Role;
import ru.naumen.ectmauth.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name="tokens")
public class Token {



    @Id
    @Column(name="token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long token_id;
    @CreationTimestamp
    private Date created;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

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

    private String refresh_token;
    private String access_token;
    private String access_token_VK;


}
