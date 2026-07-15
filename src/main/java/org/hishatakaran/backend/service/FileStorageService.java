package org.hishatakaran.backend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.hishatakaran.backend.model.EntityType;
import org.hishatakaran.backend.model.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    public List<String> uploadMedia(List<MultipartFile> files, EntityType entityType, MediaType mediaType) {

        List<String> links = new ArrayList<>();
        files.forEach(file -> {
            String extension = getExtension(
                Objects.requireNonNull(file.getOriginalFilename())
            );
            String fileName = UUID.randomUUID() + "." + extension;
            Path target = Paths.get(mediaType.name())
                .resolve(entityType.name())
                .resolve(fileName);
            try {
                Files.createDirectories(target.getParent());
                file.transferTo(target);
                links.add("/" + mediaType.name() + "/" + entityType.name() + "/" + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return links;
    }

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

    public void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isBlank()) {
            return;
        }

        try {
            Path path = Paths.get("images")
                .resolve(imagePath.replaceFirst("^/images/", ""));

            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete file: " + imagePath, e);
        }
    }

    public void deleteFile(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            return;
        }

        try {
            Path path = Paths.get("files")
                .resolve(filePath.replaceFirst("^/files/", ""));

            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete file: " + filePath, e);
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