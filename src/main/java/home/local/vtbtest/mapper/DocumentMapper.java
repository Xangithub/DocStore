package home.local.vtbtest.mapper;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.repository.ClientRepository;
import home.local.vtbtest.repository.FileRepository;
import home.local.vtbtest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DocumentMapper extends AbstractMapper<Document, DocumentDto> {

    private final ModelMapper mapper;
    private final ClientRepository clientRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    DocumentMapper(ModelMapper mapper,
                   ClientRepository clientRepository,
                   FileRepository fileRepository,
                   UserRepository userRepository
                   ) {
        super(Document.class, DocumentDto.class);
        this.mapper = mapper;
        this.clientRepository = clientRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Document.class, DocumentDto.class)
                .addMappings(m -> {
                    m.skip(DocumentDto::setClientId);
                    m.skip(DocumentDto::setFileId);
                    m.skip(DocumentDto::setUserId);
                }).setPostConverter(toDtoConverter());
        mapper.createTypeMap(DocumentDto.class, Document.class)
                .addMappings(m -> {
                    m.skip(Document::setClient);
                    m.skip(Document::setFile);
                    m.skip(Document::setUser);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    void mapSpecificFields(Document source, DocumentDto destination) {
        destination.setClientId(source.getClient().getId());
        destination.setFileId(source.getFile().getId());
        destination.setUserId(source.getUser().getId());
    }

    @Override
    void mapSpecificFields(DocumentDto source, Document destination) {
        destination.setClient(clientRepository.getOne(source.getClientId()));
        destination.setFile(fileRepository.findById(source.getFileId()).orElse (null));
        destination.setUser(userRepository.getOne(source.getUserId()));
    }
}
