package com.seal.ecommerce.service;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.seal.ecommerce.exception.AppException;
import com.seal.ecommerce.exception.ErrorCode;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path rootLocation = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_STORAGE_EXCEPTION);
        }
    }

    @Override
    public Path load(String fileUri) {
        return rootLocation.resolve(fileUri);
    }

    @Override
    public String store(MultipartFile file, String fileDir) {
        String hashedFileName = null;
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + file.getOriginalFilename());
            }

            fileDir = rootLocation.resolve(fileDir).normalize().toAbsolutePath().toString();
            Path destinationDir = Paths.get(fileDir);

            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            String originalFilename = file.getOriginalFilename();
            String hash = DigestUtils.sha256Hex(file.getInputStream());

            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf('.'))
                    : "";

            hashedFileName = hash + extension;

            Path destinationFile = destinationDir.resolve(Paths.get(hashedFileName))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(destinationDir.toAbsolutePath())) {
                throw new RuntimeException("Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile);
            }
        } catch (IOException e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new AppException(ErrorCode.FILE_ALREADY_EXISTS);
            }
            throw new RuntimeException(e.getMessage());
        }
        return hashedFileName;
    }

    @Override
    public Resource loadAsResource(String fileUri) {
        try {
            Path file = load(fileUri);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new AppException(ErrorCode.STORAGE_FILE_NOT_FOUND);
            }
        } catch (MalformedURLException e) {
            throw new AppException(ErrorCode.STORAGE_FILE_NOT_FOUND);
        }
    }

    @Override
    public void deleteAll(String fileDir) {
        FileSystemUtils.deleteRecursively(Paths.get(fileDir).toFile());
    }

    @Override
    public void delete(String fileUri) {
        Path file = load(fileUri);
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new AppException(ErrorCode.STORAGE_FILE_NOT_FOUND);
        }
    }

    @Override
    public Stream<Path> loadAll(String fileDir) {
        try {
            Path dirPath = Paths.get(fileDir);
            return Files.walk(dirPath, 1)
                    .filter(path -> !path.equals(dirPath))
                    .map(dirPath::relativize);
        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_STORAGE_EXCEPTION);
        }
    }
}