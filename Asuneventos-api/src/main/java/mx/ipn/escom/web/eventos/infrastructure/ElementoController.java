package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.ElementoService;
import mx.ipn.escom.web.eventos.application.dto.DTOMapper;
import mx.ipn.escom.web.eventos.application.dto.request.ElementoRequest;
import mx.ipn.escom.web.eventos.application.dto.response.ElementoResponse;
import mx.ipn.escom.web.eventos.domain.repository.PlanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/elementos")
@Tag(name = "Elementos", description = "Gestion de elementos de un plan")
@SecurityRequirement(name = "Bearer")
public class ElementoController {

    @Autowired
    private ElementoService elementoService;

    @Autowired
    private PlanRepository planRepository;

    @GetMapping
    @Operation(summary = "Listar todos los elementos")
    public ResponseEntity<List<ElementoResponse>> findAll() {
        return ResponseEntity.ok(elementoService.findAll().stream()
                .map(DTOMapper::toElementoResponse).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar elemento por ID")
    public ResponseEntity<ElementoResponse> findById(@PathVariable Long id) {
        return elementoService.findById(id)
                .map(DTOMapper::toElementoResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/plan/{idPlan}")
    @Operation(summary = "Buscar elementos por plan (ordenados)")
    public ResponseEntity<List<ElementoResponse>> findByPlan(@PathVariable Long idPlan) {
        return ResponseEntity.ok(elementoService.findByPlanIdOrderByOrden(idPlan).stream()
                .map(DTOMapper::toElementoResponse).toList());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Crear elemento [ADMIN]")
    public ResponseEntity<ElementoResponse> create(@Valid @RequestBody ElementoRequest request) {
        var plan = planRepository.findById(request.getIdPlan())
                .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado"));
        var elemento = DTOMapper.toElemento(request, plan);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DTOMapper.toElementoResponse(elementoService.save(elemento)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Actualizar elemento [ADMIN]")
    public ResponseEntity<ElementoResponse> update(@PathVariable Long id, @Valid @RequestBody ElementoRequest request) {
        return elementoService.findById(id)
                .flatMap(existing -> planRepository.findById(request.getIdPlan())
                        .map(plan -> {
                            existing.setPlan(plan);
                            existing.setOrden(request.getOrden());
                            existing.setTitulo(request.getTitulo());
                            existing.setDuracion(request.getDuracion());
                            return ResponseEntity.ok(DTOMapper.toElementoResponse(elementoService.save(existing)));
                        }))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar elemento [ADMIN]")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return elementoService.findById(id)
                .map(existing -> {
                    elementoService.deleteById(id);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
