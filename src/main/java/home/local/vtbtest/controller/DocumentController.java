package home.local.vtbtest.controller;

import home.local.vtbtest.dto.DocumentDto;
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
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final DocumentService documentService;
    private final StorageService storageService;

    @GetMapping("/all")
    public List<DocumentDto> readAll() {
        return documentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> readOne(@PathVariable Long id) {
        DocumentDto documentDto;
        try {
            documentDto = documentService.getDocument(id);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(documentDto);
    }

    @GetMapping(value = "/{id}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getFileFromDoc(@PathVariable Long id) {
        final DocumentDto docDto;
        try {
            docDto = documentService.getDocument(id);
        } catch (Exception e) {
           return ResponseEntity.notFound().build();
        }
        Resource r = storageService.loadAsResource(docDto.getFileId());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + r.getDescription() + "\"").body(r);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> save(@RequestPart("document") DocumentDto document, MultipartFile file) {
        try {
            final Long idDoc = documentService.save(document, file);
            if (idDoc == null) {
                return ResponseEntity.notFound().build();
            } else
                return ResponseEntity.ok(idDoc);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        documentService.deleteById(id);
    }
}
