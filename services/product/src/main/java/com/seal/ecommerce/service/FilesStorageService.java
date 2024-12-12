package com.seal.ecommerce.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public String store(MultipartFile file, String fileDir);

    public Resource loadAsResource(String fileUri);

    public void deleteAll(String fileDir);

    public void delete(String fileUri);

    public Path load(String fileUir);

    public Stream<Path> loadAll(String fileDir);
}