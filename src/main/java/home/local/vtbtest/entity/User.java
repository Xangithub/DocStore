package home.local.vtbtest.entity;

import home.local.vtbtest.service.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/***
        * Пользователь
        ·         Логин
        ·         Пароль в зашифрованном виде
        ·         ФИО
        ·         Дата регистрации
*/

@Entity
@RequiredArgsConstructor
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
//    @OneToMany(mappedBy = )
    @Transient
    private List<Role> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
}
