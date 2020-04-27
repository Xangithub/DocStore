package home.local.vtbtest.service;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private  final DocumentRepository documentRepository;
    private final StorageService storageService;
    private final UserService userService;


    public Long save(DocumentDto document, MultipartFile file) {
        //логика сохранения документов

//        storageService.store(file);
//        final Document doc = documentRepository.save(document);
        return null;
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
