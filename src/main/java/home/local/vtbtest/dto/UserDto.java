package home.local.vtbtest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/***
        * Пользователь
        ·         Логин
        ·         Пароль в зашифрованном виде
        ·         ФИО
*/

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class UserDto extends AbstractDto{
    @NotNull
    String username;
    @NotNull
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String password;
    String fullName;
}
