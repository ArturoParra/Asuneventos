package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Elemento;

import java.util.List;
import java.util.Optional;

public interface ElementoService {

    List<Elemento> findAll();

    Optional<Elemento> findById(Long id);

    Elemento save(Elemento elemento);

    void deleteById(Long id);

    List<Elemento> findByPlanIdOrderByOrden(Long idPlan);
}
