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

    public String saveImage(MultipartFile file, String folder) {

        String extension = getExtension(
                Objects.requireNonNull(file.getOriginalFilename())
        );

        String fileName = UUID.randomUUID() + "." + extension;

        Path target = Paths.get("images")
            .resolve(folder)
            .resolve(fileName);

        try {
            Files.createDirectories(target.getParent());
            file.transferTo(target);
            return "/images/" + folder + "/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String savefile(MultipartFile file, String folder) {

        String extension = getExtension(
            Objects.requireNonNull(file.getOriginalFilename())
        );

        String fileName = UUID.randomUUID() + "." + extension;

        Path target = Paths.get("files")
            .resolve(folder)
            .resolve(fileName);

        try {
            Files.createDirectories(target.getParent());
            file.transferTo(target);
            return "/files/" + folder + "/" + fileName;
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