package home.local.vtbtest.dto;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


/***
 Файл
 ·         Наименование
 ·         Контент
 */
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class FileDto extends AbstractDto{
    private String fileName;
    private String fileKey;
    private byte[] data;
}
