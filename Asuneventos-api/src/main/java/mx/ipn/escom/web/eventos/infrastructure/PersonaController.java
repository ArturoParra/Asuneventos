package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.PersonaService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.PersonaRequest;
import mx.ipn.escom.web.eventos.application.dto.response.PersonaResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/personas")
@Tag(name = "Personas", description = "Gestion del personal registrado")
@SecurityRequirement(name = "Bearer")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @Operation(summary = "Listar todas las personas")
    public ResponseEntity<List<PersonaResponse>> findAll() {
        return ResponseEntity.ok(personaService.findAll().stream()
                .map(DTOMapper::toPersonaResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar persona por ID")
    public ResponseEntity<PersonaResponse> findById(@PathVariable Long id) {
        return personaService.findById(id)
                .map(DTOMapper::toPersonaResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar persona")
    @ApiResponse(responseCode = "201", description = "Persona creada")
    public ResponseEntity<PersonaResponse> create(@Valid @RequestBody PersonaRequest request) {
        var persona = DTOMapper.toPersona(request);
        persona.setPassword(passwordEncoder.encode(persona.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toPersonaResponse(personaService.save(persona)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar persona")
    public ResponseEntity<PersonaResponse> update(@PathVariable Long id, @Valid @RequestBody PersonaRequest request) {
        return personaService.findById(id)
                .map(existing -> {
                    existing.setNombre(request.getNombre());
                    existing.setEmail(request.getEmail());
                    if (request.getPassword() != null && !request.getPassword().isBlank()) {
                        existing.setPassword(passwordEncoder.encode(request.getPassword()));
                    }
                    if (request.getRol() != null) {
                        existing.setRol(request.getRol());
                    }
                    return ResponseEntity.ok(DTOMapper.toPersonaResponse(personaService.save(existing)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar persona")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return personaService.findById(id)
                .map(existing -> {
                    personaService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
