package home.local.vtbtest.controller;

import home.local.vtbtest.dto.DocumentDto;
import home.local.vtbtest.entity.Document;
import home.local.vtbtest.entity.File;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.service.DocumentService;
import home.local.vtbtest.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/***
 ·         Размещение (метаданные + файл)

 ·         Получение метаданных

 ·         Получение файла

 ·         Удаление
 */

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentController {

   private final DocumentRepository documentRepository;
   private final DocumentService documentService;
   private final StorageService storageService;

    @GetMapping("/all")
    List<Document> readAll() {
        return documentService.findAll();
    }

    @GetMapping("/document/{id}")
    Document readOne(@PathVariable Long id)  {
        return documentService.getDocument(id);
    }

    @GetMapping(value = "/document/{id}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    ResponseEntity<Resource> getFileFromDoc(@PathVariable Long id)  {
        final Document doc = documentService.getDocument(id);
        final File file = doc.getFile();

        Resource r = storageService.loadAsResource(file.getId());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + r.getDescription() + "\"").body(r);
    }

    @PostMapping("/create")
    ResponseEntity<Long> save(@RequestPart("document") DocumentDto document, MultipartFile file) {
        try {
            final Long idDoc = documentService.save(document, file);
            if (idDoc == null) {
                return ResponseEntity.notFound().build();
            } else
            return  ResponseEntity.ok(idDoc);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Long id) throws Exception {
        documentService.deleteById(id);
    }
}
