package home.local.vtbtest.model;

import home.local.vtbtest.entity.Client;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
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
@Entity
@Table(name = "Documents")
@RequiredArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class Document {
    @Id @GeneratedValue
    private Integer id;
    @OneToOne
    private Client client;
    @OneToOne
    private File file;
    private LocalDate docDate;
    @OneToOne
    private User user;  // Пользователь, разместивший документ
    private LocalDate docCreateDate;
}
