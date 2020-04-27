package home.local.vtbtest.entity;

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
@Table(name = "documents")
@RequiredArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class Document extends AbstractEntity{
    private Integer number;
    @ManyToOne
//    @JoinColumn(name="CUST_ID", nullable=false)
    private Client client;
    @OneToOne
    private File file;
    private LocalDate docDate; //бизнес дата документа
    private User user;  // Пользователь, разместивший документ
}
