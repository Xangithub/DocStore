package home.local.vtbtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class ClientDto {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String inn;
    private String ogrn;
    private LocalDate createDate;
    Integer documentId;
}
