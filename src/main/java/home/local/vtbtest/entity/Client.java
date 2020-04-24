package home.local.vtbtest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class Client {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String inn;
    private String ogrn;
    private LocalDate respawnDate;
}
