package com.portafolio.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Servicio encargado de almacenar archivos subidos (imagenes, PDFs) en disco,
 * fuera del classpath, para que puedan servirse como recursos estaticos.
 */
@Service
public class FileStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path rootLocation;

    @PostConstruct
    public void init() {
        rootLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar el directorio de subida de archivos", e);
        }
    }

    /**
     * Guarda el archivo y devuelve el nombre unico generado (para persistir en BD).
     * Devuelve null si el archivo esta vacio.
     */
    public String store(MultipartFile file, String subfolder) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            String original = StringUtils.cleanPath(file.getOriginalFilename() == null ? "archivo" : file.getOriginalFilename());
            String extension = "";
            int dot = original.lastIndexOf('.');
            if (dot >= 0) {
                extension = original.substring(dot);
            }
            String filename = UUID.randomUUID() + extension;

            Path folder = rootLocation.resolve(subfolder);
            Files.createDirectories(folder);

            Path destination = folder.resolve(filename).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

            return subfolder + "/" + filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }

    public void delete(String relativePath) {
        if (relativePath == null || relativePath.isBlank()) return;
        try {
            Path file = rootLocation.resolve(relativePath).normalize();
            Files.deleteIfExists(file);
        } catch (IOException ignored) {
            // No es critico si falla el borrado de un archivo huerfano
        }
    }
}
