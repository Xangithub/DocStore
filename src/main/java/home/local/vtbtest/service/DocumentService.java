package home.local.vtbtest.service;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.dto.UserDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.File;
import home.local.vtbtest.mapper.DocumentMapper;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private  final DocumentRepository documentRepository;
    private  final DocumentMapper documentMapper;
    private final StorageService storageService;
    private final UserService userService;
    private final ClientService clientService;


    public Long save(DocumentDto documentDto, MultipartFile multipartFile) throws Exception {
        final Long clientId = documentDto.getClientId();
        final Long userId = documentDto.getUserId();
        clientService.findOne(clientId).orElseThrow(() -> new Exception("клиент с id= " + clientId + "отсутствует"));
        final Optional<UserDto> userOpt = userService.getUser(userId);
        userOpt.orElseThrow(() -> new Exception("пользователь с id= " + userId + "отсутствует"));
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

    public Document getDocument(Long id) {
        return documentRepository.getOne(id);
    }

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }
}
