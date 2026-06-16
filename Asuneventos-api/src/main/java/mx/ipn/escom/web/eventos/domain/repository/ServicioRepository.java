package mx.ipn.escom.web.eventos.domain.repository;

import mx.ipn.escom.web.eventos.domain.entities.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {

    List<Servicio> findByNombreContainingIgnoreCase(String nombre);
}
