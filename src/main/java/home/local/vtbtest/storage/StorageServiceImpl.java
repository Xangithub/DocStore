package home.local.vtbtest.storage;

import home.local.vtbtest.entity.File;
import home.local.vtbtest.repository.FileRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private FileRepository fileRepository;

    final static long MAX_FILE_SIZE = 10 * 1024 * 1024;

    public StorageServiceImpl(StorageProperties properties, FileRepository fileRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileRepository = fileRepository;
    }

    @Transactional(rollbackFor = {IOException.class})
    @Override
    public File store(MultipartFile multipartFile) {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        final long fileSize = multipartFile.getSize();

        if (multipartFile.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        File file = null;
        if (fileSize > MAX_FILE_SIZE) { //кладём файл в ФС
            try (InputStream inputStream = multipartFile.getInputStream()){
                final String key = generateKey(filename);
                Files.copy(inputStream, this.rootLocation.resolve(key));

                file = new File().
                        setFileName(filename).
                        setFileKey(key);
                fileRepository.save(file);
            } catch (IOException e) {
                throw new StorageException("Failed to store file " + filename, e);
            }

        } else { //кладём файл в базу
            try {
                file = new File().
                        setData(multipartFile.getBytes()).
                        setFileName(filename);
                fileRepository.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


   /* public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    } */

    @Override
    public Map<Long, Path> loadAll() {
    /*    try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }*/
        final Map<Long, Path> all= new HashMap<>();
        final Iterable<File> fileRepositoryAll = fileRepository.findAll();
        fileRepositoryAll.forEach(file -> all.put(file.getId(),Paths.get(file.getFileName())));
        return  all;
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


    @Override
    public Resource loadAsResource(Long fileId) {
        final Optional<File> fileById = fileRepository.findById(fileId);
        final File file = fileById.orElseThrow(() -> new StorageFileNotFoundException("Could not read file with id " + fileById));
        if(file.getData()==null){

            try {
                Path pathFile = load(file.getFileKey());
                if (Files.exists(pathFile) || Files.isReadable(pathFile)) {
                    return new ByteArrayResource(Files.readAllBytes(pathFile),file.getFileName());

                } else {
                    throw new StorageFileNotFoundException(
                            "Could not read file: " + file.getFileName());
                }
            } catch (IOException e) {
                throw new StorageFileNotFoundException("Could not read file: " + file.getFileName(), e);
            }
        } else {
            return new ByteArrayResource(file.getData(),file.getFileName());
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }



    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void delete(Integer id) {
//        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    private String generateKey(String name) {
        return DigestUtils.md5DigestAsHex((name + LocalDateTime.now().toString()).getBytes());
    }
}