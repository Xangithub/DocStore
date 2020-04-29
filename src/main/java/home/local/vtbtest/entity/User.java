package home.local.vtbtest.entity;

import home.local.vtbtest.config.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

/***
        * Пользователь
        ·         Логин
        ·         Пароль в зашифрованном виде
        ·         ФИО
        ·         Дата регистрации
*/

@Entity
@Accessors(chain = true)
@Setter
@Getter
public class User extends AbstractEntity implements UserDetails
{
    @Column(name = "login", updatable = false)
    private String username;
    @Column(name = "pass_hash")
    private String password;;
    String fullName;
    @OneToMany(mappedBy = "user" )
    List<Document> documentList;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
}
