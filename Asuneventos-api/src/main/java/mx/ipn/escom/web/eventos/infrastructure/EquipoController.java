package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.EquipoService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.EquipoRequest;
import mx.ipn.escom.web.eventos.application.dto.response.EquipoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
@Tag(name = "Equipos", description = "Gestion de equipos de trabajo")
@SecurityRequirement(name = "Bearer")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @GetMapping
    @Operation(summary = "Listar todos los equipos")
    public ResponseEntity<List<EquipoResponse>> findAll() {
        return ResponseEntity.ok(equipoService.findAll().stream()
                .map(DTOMapper::toEquipoResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar equipo por ID")
    public ResponseEntity<EquipoResponse> findById(@PathVariable Long id) {
        return equipoService.findById(id)
                .map(DTOMapper::toEquipoResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear equipo")
    @ApiResponse(responseCode = "201", description = "Equipo creado")
    public ResponseEntity<EquipoResponse> create(@Valid @RequestBody EquipoRequest request) {
        var equipo = DTOMapper.toEquipo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toEquipoResponse(equipoService.save(equipo)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar equipo")
    public ResponseEntity<EquipoResponse> update(@PathVariable Long id, @Valid @RequestBody EquipoRequest request) {
        return equipoService.findById(id)
                .map(existing -> {
                    existing.setNombreEquipo(request.getNombreEquipo());
                    return ResponseEntity.ok(DTOMapper.toEquipoResponse(equipoService.save(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar equipo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return equipoService.findById(id)
                .map(existing -> {
                    equipoService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
