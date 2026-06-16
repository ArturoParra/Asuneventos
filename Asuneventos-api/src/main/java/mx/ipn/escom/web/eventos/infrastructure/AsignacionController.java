package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.AsignacionService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.AsignacionRequest;
import mx.ipn.escom.web.eventos.application.dto.response.AsignacionResponse;
import mx.ipn.escom.web.eventos.domain.repository.EquipoRepository;
import mx.ipn.escom.web.eventos.domain.repository.PersonaRepository;
import mx.ipn.escom.web.eventos.domain.repository.PlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/asignaciones")
@Tag(name = "Asignaciones", description = "Gestion de asignaciones de personal a planes")
@SecurityRequirement(name = "Bearer")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @GetMapping
    @Operation(summary = "Listar todas las asignaciones")
    public ResponseEntity<List<AsignacionResponse>> findAll() {
        return ResponseEntity.ok(asignacionService.findAll().stream()
                .map(DTOMapper::toAsignacionResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar asignacion por ID")
    public ResponseEntity<AsignacionResponse> findById(@PathVariable Long id) {
        return asignacionService.findById(id)
                .map(DTOMapper::toAsignacionResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/plan/{idPlan}")
    @Operation(summary = "Buscar asignaciones por plan")
    public ResponseEntity<List<AsignacionResponse>> findByPlan(@PathVariable Long idPlan) {
        return ResponseEntity.ok(asignacionService.findByPlanId(idPlan).stream()
                .map(DTOMapper::toAsignacionResponse).toList());
    }

    @GetMapping("/persona/{idPersona}")
    @Operation(summary = "Buscar asignaciones por persona")
    public ResponseEntity<List<AsignacionResponse>> findByPersona(@PathVariable Long idPersona) {
        return ResponseEntity.ok(asignacionService.findByPersonaId(idPersona).stream()
                .map(DTOMapper::toAsignacionResponse).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear asignacion [ADMIN]")
    @ApiResponse(responseCode = "201", description = "Asignacion creada y notificacion enviada")
    public ResponseEntity<AsignacionResponse> create(@Valid @RequestBody AsignacionRequest request) {
        var persona = personaRepository.findById(request.getIdPersona())
                .orElseThrow(() -> new IllegalArgumentException("Persona no encontrada"));
        var plan = planRepository.findById(request.getIdPlan())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));
        var equipo = equipoRepository.findById(request.getIdEquipo())
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));
        var asignacion = DTOMapper.toAsignacion(request, persona, plan, equipo);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toAsignacionResponse(asignacionService.save(asignacion)));
    }

    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VOLUNTARIO')")
    @Operation(summary = "Actualizar estado [ADMIN / VOLUNTARIO]")
    public ResponseEntity<AsignacionResponse> updateEstado(
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Nuevo estado: Pendiente, Confirmado o Rechazado",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(example = "{\"estado\": \"Confirmado\"}")
                )
            )
            @RequestBody Map<String, String> body) {
        return asignacionService.updateEstado(id, body.get("estado"))
                .map(DTOMapper::toAsignacionResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar asignacion [ADMIN]")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return asignacionService.findById(id)
                .map(existing -> {
                    asignacionService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
