package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.ServicioService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.ServicioRequest;
import mx.ipn.escom.web.eventos.application.dto.response.ServicioResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/servicios")
@Tag(name = "Servicios", description = "Gestion de servicios de eventos")
@SecurityRequirement(name = "Bearer")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping
    @Operation(summary = "Listar todos los servicios")
    public ResponseEntity<List<ServicioResponse>> findAll() {
        return ResponseEntity.ok(servicioService.findAll().stream()
                .map(DTOMapper::toServicioResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar servicio por ID")
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    public ResponseEntity<ServicioResponse> findById(@PathVariable Long id) {
        return servicioService.findById(id)
                .map(DTOMapper::toServicioResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear servicio [ADMIN]")
    @ApiResponse(responseCode = "201", description = "Servicio creado")
    public ResponseEntity<ServicioResponse> create(@Valid @RequestBody ServicioRequest request) {
        var entity = DTOMapper.toServicio(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toServicioResponse(servicioService.save(entity)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Actualizar servicio [ADMIN]")
    public ResponseEntity<ServicioResponse> update(@PathVariable Long id, @Valid @RequestBody ServicioRequest request) {
        return servicioService.findById(id)
                .map(existing -> {
                    existing.setNombre(request.getNombre());
                    existing.setDescripcion(request.getDescripcion());
                    return ResponseEntity.ok(DTOMapper.toServicioResponse(servicioService.save(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar servicio [ADMIN]")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return servicioService.findById(id)
                .map(existing -> {
                    servicioService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
