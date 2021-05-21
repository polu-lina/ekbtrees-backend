package ru.naumen.ectmauth.entity;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long user_id;
    @CreationTimestamp
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Дата создания пользователя")
    private Date created;
    @Schema(description = "Почта", example = "naumen@mail.ru")
    private String email;
    @Schema(description = "Имя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;
    @Schema(description = "Пароль", example = "p1a2s3s4w5o6r7d")
    private String password;
    @Schema(description = "Телефон", example = "+79222298897")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Провайдер", example = "VK")
    private Provider provider;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private boolean enabled;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор пользователя Вконтакте")
    private String vk_id;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Идентификатор пользователя Вконтакте")
    private String fb_id;


    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @OneToMany(mappedBy = "user")
    private Set<Token> tokens = new HashSet<Token>();

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getVk_id() {
        return vk_id;
    }

    public void setVk_id(String vk_id) {
        this.vk_id = vk_id;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }
}
