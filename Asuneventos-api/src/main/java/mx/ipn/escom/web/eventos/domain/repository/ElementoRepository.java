package mx.ipn.escom.web.eventos.domain.repository;

import mx.ipn.escom.web.eventos.domain.entities.Elemento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElementoRepository extends JpaRepository<Elemento, Long> {

    List<Elemento> findByPlan_IdPlan(Long idPlan);

    List<Elemento> findByPlan_IdPlanOrderByOrdenAsc(Long idPlan);
}
