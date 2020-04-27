package home.local.vtbtest.mapper;

import home.local.vtbtest.dto.ClientDto;
import home.local.vtbtest.entity.Client;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.repository.DocumentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class ClientMapper extends AbstractMapper<Client, ClientDto> {

    private final ModelMapper mapper;
    private final DocumentRepository documentRepository;

    ClientMapper(ModelMapper mapper, DocumentRepository documentRepository) {
        super(Client.class,ClientDto.class);
        this.mapper = mapper;
        this.documentRepository = documentRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Client.class, ClientDto.class)
                .addMappings(m -> m.skip(ClientDto::setDocumentsIdList)).setPostConverter(toDtoConverter());
        mapper.createTypeMap(ClientDto.class, Client.class)
                .addMappings(m -> m.skip(Client::setDocumentsList)).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Client source, ClientDto destination) {
        final List<Document> documentsList = source.getDocumentsList();
        for (Document document : documentsList) {
            destination.getDocumentsIdList().add(document.getId());
        }
    }

    @Override
    void mapSpecificFields(ClientDto source, Client destination) {
        source.getDocumentsIdList().forEach(id -> {
            final Optional<Document> docOpt = documentRepository.findById(id);
            docOpt.ifPresent(document ->  destination.getDocumentsList().add(document));
        });
    }
}
