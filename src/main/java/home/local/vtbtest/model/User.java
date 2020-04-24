package home.local.vtbtest.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
public class User {
    @Id @GeneratedValue
    Integer id;
    String login;
    String hashPass;
    String pass;
    String fullName;
}
