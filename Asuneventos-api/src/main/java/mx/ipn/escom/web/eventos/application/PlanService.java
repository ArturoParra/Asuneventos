package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Plan;

import java.util.List;
import java.util.Optional;

public interface PlanService {

    List<Plan> findAll();

    Optional<Plan> findById(Long id);

    Plan save(Plan plan);

    void deleteById(Long id);

    List<Plan> findByServicioId(Long idServicio);
}
