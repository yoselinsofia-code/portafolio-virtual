
package com.portafolio.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageService(
            @Value("${CLOUDINARY_CLOUD_NAME}") String cloudName,
            @Value("${CLOUDINARY_API_KEY}") String apiKey,
            @Value("${CLOUDINARY_API_SECRET}") String apiSecret) {

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }

    public String store(MultipartFile file, String subfolder) {

        if (file == null || file.isEmpty()) {
            return null;
        }

        try {
            // Obtener nombre original
            String original = file.getOriginalFilename();

            // Obtener extensión
            String extension = "";

            if (original != null && original.contains(".")) {
                extension = original.substring(
                        original.lastIndexOf(".")
                ).toLowerCase();
            }

            // Detectar archivos PDF
            boolean esPdf = extension.equals(".pdf");

            // Cloudinary: PDF como raw, fotos como image
            String tipo = esPdf ? "raw" : "image";

            // Generar nombre único
            String nombre = UUID.randomUUID().toString();

            // Los archivos raw deben conservar su extensión
            if (esPdf) {
                nombre += ".pdf";
            }

            // Subir a Cloudinary
            Map<?, ?> resultado = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "portafolio/" + subfolder,
                            "public_id", nombre,
                            "resource_type", tipo
                    )
            );

            // Devolver URL segura
            Object url = resultado.get("secure_url");

            if (url == null) {
                throw new RuntimeException(
                        "Cloudinary no devolvió una URL segura."
                );
            }

            return url.toString();

        } catch (IOException e) {
            throw new RuntimeException(
                    "Error al subir archivo a Cloudinary: "
                            + e.getMessage(), e
            );
        }
    }

    public void delete(String relativePath) {

        if (relativePath == null || relativePath.isBlank()) {
            return;
        }

        // Por seguridad, no eliminamos archivos todavía.
        // Se implementará cuando configuremos
        // la eliminación mediante public_id.
    }
}