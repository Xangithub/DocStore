package home.local.vtbtest.controller;

import home.local.vtbtest.entity.Client;
import home.local.vtbtest.model.Document;
import home.local.vtbtest.repository.DocumentRepository;
import home.local.vtbtest.service.ClientService;
import home.local.vtbtest.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

   private final DocumentRepository documentRepository;

    @GetMapping("/all")
    List<Document> readAll() {
        return documentRepository.findAll();
    }

    @GetMapping("/document/{id}")
    Document readOne(@PathVariable Integer id)  {
        return documentRepository.getOne(id);
    }

    @PostMapping("/create")
    Document save(@RequestBody Document document) throws Exception {
        return documentRepository.save(document);
    }

    @DeleteMapping("/delete/{id}")
    void delete(@PathVariable Integer id) throws Exception {
        documentRepository.deleteById(id);
    }
}
