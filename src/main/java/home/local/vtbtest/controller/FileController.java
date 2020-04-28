package home.local.vtbtest.controller;

import home.local.vtbtest.storage.StorageFileNotFoundException;
import home.local.vtbtest.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
/***
        ·         Размещение (метаданные + файл)

        ·         Получение метаданных

        ·         Получение файла

        ·         Удаление
 */

@Controller
public class FileController {
    private final StorageService storageService;
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

   /* @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }*/

    @GetMapping("/files/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFileId(@PathVariable Long id) {

        Resource file = storageService.loadAsResource(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getDescription() + "\"").body(file);
    }

    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        /*    model.addAttribute("files", storageService.loadAll().stream().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList())); */
        model.addAttribute("files", storageService.loadAll());
        return "uploadForm";
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @DeleteMapping("/files/delete/{id}")
    public String deleteFile(@PathVariable Integer id) {
        storageService.delete(id);
        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
