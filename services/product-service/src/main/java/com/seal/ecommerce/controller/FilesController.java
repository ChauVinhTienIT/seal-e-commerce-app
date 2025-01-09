package com.seal.ecommerce.controller;

import com.seal.ecommerce.service.FilesStorageService;
import jakarta.xml.bind.SchemaOutputResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FilesController {
    private final FilesStorageService storageService;

    @PostMapping(path = "/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {
        String message = "";
        try {
            storageService.store(file, "upload-dir");

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(message);

        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Resource> getFile(
            @RequestParam("fileUri") String fileUri
    ) throws IOException {
        System.out.println(fileUri);
        String decodedFileUri = java.net.URLDecoder.decode(fileUri, StandardCharsets.UTF_8);
        System.out.println(decodedFileUri);
        Resource file = storageService.loadAsResource(decodedFileUri);

        // Decode the received file URI
        String contentType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }
}

