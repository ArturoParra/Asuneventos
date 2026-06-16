package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Asignacion;

import java.util.List;
import java.util.Optional;

public interface AsignacionService {

    List<Asignacion> findAll();

    Optional<Asignacion> findById(Long id);

    Asignacion save(Asignacion asignacion);

    void deleteById(Long id);

    List<Asignacion> findByPlanId(Long idPlan);

    List<Asignacion> findByPersonaId(Long idPersona);

    Optional<Asignacion> updateEstado(Long id, String estado);
}
