package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.PlanService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.PlanRequest;
import mx.ipn.escom.web.eventos.application.dto.response.PlanResponse;
import mx.ipn.escom.web.eventos.domain.repository.ServicioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/planes")
@Tag(name = "Planes", description = "Gestion de planes de eventos")
@SecurityRequirement(name = "Bearer")
public class PlanController {

    @Autowired
    private PlanService planService;

    @Autowired
    private ServicioRepository servicioRepository;

    @GetMapping
    @Operation(summary = "Listar todos los planes")
    public ResponseEntity<List<PlanResponse>> findAll() {
        return ResponseEntity.ok(planService.findAll().stream()
                .map(DTOMapper::toPlanResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plan por ID")
    public ResponseEntity<PlanResponse> findById(@PathVariable Long id) {
        return planService.findById(id)
                .map(DTOMapper::toPlanResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/servicio/{idServicio}")
    @Operation(summary = "Buscar planes por servicio")
    public ResponseEntity<List<PlanResponse>> findByServicio(@PathVariable Long idServicio) {
        return ResponseEntity.ok(planService.findByServicioId(idServicio).stream()
                .map(DTOMapper::toPlanResponse).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear plan [ADMIN]")
    public ResponseEntity<PlanResponse> create(@Valid @RequestBody PlanRequest request) {
        var servicio = servicioRepository.findById(request.getIdServicio())
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        var plan = DTOMapper.toPlan(request, servicio);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toPlanResponse(planService.save(plan)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Actualizar plan [ADMIN]")
    public ResponseEntity<PlanResponse> update(@PathVariable Long id, @Valid @RequestBody PlanRequest request) {
        return planService.findById(id)
                .flatMap(existing -> servicioRepository.findById(request.getIdServicio())
                        .map(servicio -> {
                            existing.setServicio(servicio);
                            existing.setFecha(request.getFecha());
                            existing.setTituloSerie(request.getTituloSerie());
                            return ResponseEntity.ok(DTOMapper.toPlanResponse(planService.save(existing)));
                        }))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar plan [ADMIN]")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return planService.findById(id)
                .map(existing -> {
                    planService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
