package home.local.vtbtest.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/***
 Документ
 ·         Номер документа
 ·         Клиент
 ·         Файл
 ·         Дата документа
 ·         Пользователь, разместивший документ
 ·         Дата создания
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class DocumentDto {
    private Long id;
    private String number; // нумерация док-ов может включать буквы
    private Integer clientId;
    private Integer fileId;
    private LocalDate docDate;
    private Integer userId;
    private LocalDate docCreateDate;
}
