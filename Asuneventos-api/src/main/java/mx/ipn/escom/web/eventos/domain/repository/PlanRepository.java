package mx.ipn.escom.web.eventos.domain.repository;

import mx.ipn.escom.web.eventos.domain.entities.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> findByServicio_IdServicio(Long idServicio);

    List<Plan> findByFecha(LocalDate fecha);

    List<Plan> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
