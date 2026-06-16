package mx.ipn.escom.web.eventos.domain.repository;

import mx.ipn.escom.web.eventos.domain.entities.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByPlan_IdPlan(Long idPlan);

    List<Asignacion> findByPersona_IdPersona(Long idPersona);

    List<Asignacion> findByEquipo_IdEquipo(Long idEquipo);

    List<Asignacion> findByEstado(String estado);

    Optional<Asignacion> findByPersona_IdPersonaAndPlan_IdPlanAndEquipo_IdEquipo(
            Long idPersona, Long idPlan, Long idEquipo);
}
