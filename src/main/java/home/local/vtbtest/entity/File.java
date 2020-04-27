package home.local.vtbtest.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


/***
 Файл
 ·         Наименование
 ·         Контент
 */
@Entity
@Table(name = "data_storage")
@RequiredArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class File extends AbstractEntity{
    private String fileName;
    private String fileKey;
    @Lob
    private byte[] data;
}
