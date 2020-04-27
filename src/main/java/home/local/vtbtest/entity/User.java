package home.local.vtbtest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;

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
public class User extends AbstractEntity{
    @Column(name = "login", updatable = false)
    String login;
    @Column(name = "pass_hash")
    String passHash;
    String fullName;
}
