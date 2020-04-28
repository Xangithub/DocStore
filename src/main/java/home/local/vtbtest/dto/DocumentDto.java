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
public class DocumentDto extends AbstractDto {
    private String docNumber; // нумерация док-ов может включать буквы
    private Long clientId;
    private Long fileId;
    private LocalDate docDate;
    private Long userId;
}
