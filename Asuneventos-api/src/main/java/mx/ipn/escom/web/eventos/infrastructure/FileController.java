package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/archivos")
@Tag(name = "Archivos", description = "Subida de archivos")
@SecurityRequirement(name = "Bearer")
public class FileController {

    private static final String UPLOAD_DIR = "uploads";

    @Operation(summary = "Subir archivo", description = "Sube un archivo al servidor. Maximo 10MB.")
    @ApiResponse(responseCode = "201", description = "Archivo subido exitosamente")
    @ApiResponse(responseCode = "400", description = "Archivo vacio o invalido")
    @PostMapping
    public ResponseEntity<?> uploadFile(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Archivo a subir",
                required = true,
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "multipart/form-data",
                    schema = @Schema(type = "object")
                )
            )
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El archivo esta vacio"));
        }

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "Archivo subido exitosamente", "filename", filename)
            );
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al guardar el archivo: " + e.getMessage()));
        }
    }
}
