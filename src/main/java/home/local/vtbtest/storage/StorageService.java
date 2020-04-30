package home.local.vtbtest.storage;

import home.local.vtbtest.entity.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Map;

public interface StorageService {
    void init();

    File store(MultipartFile file);

    Map<Long, Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    Resource loadAsResource(Long fileId);

    void deleteAll();

    void delete(Long id);
}
