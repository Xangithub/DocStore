package home.local.vtbtest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
public class ClientDto extends AbstractDto {
    private String name;
    @NotNull
    private String inn;
    private String ogrn;
    private List<Integer> documentId;
}
