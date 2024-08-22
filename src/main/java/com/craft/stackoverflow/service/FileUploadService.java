package com.craft.stackoverflow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {
    @Value("$multimedia.storage.path")
    private String storagePath;

    public String uploadFile(MultipartFile multipartFile) {
        Path rootPath = Paths.get(storagePath);
        try(InputStream inputStream = multipartFile.getInputStream()) {
            String fileNameWithExtension = Paths.get(multipartFile.getOriginalFilename())
                    .getFileName().toString();
            Path path = rootPath.resolve(fileNameWithExtension);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return fileNameWithExtension;
        }catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}
