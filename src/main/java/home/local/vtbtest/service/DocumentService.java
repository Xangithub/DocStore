package home.local.vtbtest.service;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.File;
import home.local.vtbtest.exception.EntityWithIdNotFound;
import home.local.vtbtest.mapper.DocumentMapper;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private  final DocumentRepository documentRepository;
    private  final DocumentMapper documentMapper;
    private final StorageService storageService;
    private final UserService userService;
    private final ClientService clientService;


    public Long save(DocumentDto documentDto, MultipartFile multipartFile) throws EntityWithIdNotFound {
        final Long clientId = documentDto.getClientId();
        final Long userId = documentDto.getUserId();
        clientService.findOne(clientId).orElseThrow(() -> new EntityWithIdNotFound("клиент с id= " + clientId + " отсутствует"));
        final Optional<UserDto> userOpt = userService.getUser(userId);
        userOpt.orElseThrow(() -> new EntityWithIdNotFound("пользователь с id= " + userId + " отсутствует"));
        final File file = storageService.store(multipartFile);
        if (file == null) {
            return  null;
        }
        documentDto.setFileId(file.getId());
        Document document = documentMapper.toEntity(documentDto);
        document = documentRepository.save(document);
        return document.getId();
    }

    void deleteAll() {
        documentRepository.deleteAll();
    }

    public DocumentDto getDocument(Long id) throws EntityWithIdNotFound {
        final Document document;
        try {
            document = documentRepository.getOne(id);
        } catch (PersistenceException e) {
            throw new EntityWithIdNotFound("Документ с id= " + id + "отсутствует");
        }
        return documentMapper.toDto(document);
    }

    public List<DocumentDto> findAll() {
        return  documentRepository.findAll().stream().map(document -> documentMapper.toDto(document)).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        documentRepository.deleteById(id);
        storageService.delete(id);
    }
}
