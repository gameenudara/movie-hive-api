package com.movieHive.service.impl;

import com.movieHive.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @Value("${project.poster}")
    private String posterDir;

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // Get the name of the file
        String fileName = file.getOriginalFilename();

        // Combine the base path (posterDir) with the provided path
        Path basePath = Paths.get(posterDir, path);

        // Ensure the parent directory exists
        if (!Files.exists(basePath)) {
            Files.createDirectories(basePath);
        }

        // Get the file path
        Path filePath = basePath.resolve(fileName);

        // Copy the file or upload the file to the path
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        String filePath = posterDir + File.separator + path + File.separator + filename;
        return new FileInputStream(filePath);
    }
}
