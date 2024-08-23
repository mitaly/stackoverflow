package com.craft.stackoverflow.service;

import com.craft.stackoverflow.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class FileUploadService {
    @Value("${multimedia.storage.path}")
    private String storagePath;

    public String uploadFile(MultipartFile multipartFile) {
        Path rootPath = Paths.get(storagePath);
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String targetFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
            String targetFileNameWithExtension = Paths.get(targetFileName)
                    .getFileName().toString();
            Path path = rootPath.resolve(targetFileNameWithExtension);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            return targetFileNameWithExtension;
        } catch (IOException e) {
            throw new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "file.upload.error", e.getMessage());
        }
    }
}
