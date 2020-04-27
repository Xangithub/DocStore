package home.local.vtbtest.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class Client extends AbstractEntity {
    @NotNull
    private String inn;
    private String name;
    private String ogrn;
    @OneToMany(mappedBy = "client")
    List<Document> documentsList;
}
