package org.hishatakaran.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("images");

    public String saveNewsImage(MultipartFile file) {

        String extension = getExtension(
                Objects.requireNonNull(file.getOriginalFilename())
        );

        String fileName =
                UUID.randomUUID() + "." + extension;

        Path target =
                rootLocation
                        .resolve("news")
                        .resolve(fileName);

        try {

            Files.createDirectories(target.getParent());

            file.transferTo(target);

            return "/images/news/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getExtension(String filename) {

        int index = filename.lastIndexOf('.');

        if (index < 0) {
            return "";
        }

        return filename.substring(index + 1);
    }
}