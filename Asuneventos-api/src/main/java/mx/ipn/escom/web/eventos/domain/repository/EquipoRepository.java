package mx.ipn.escom.web.eventos.domain.repository;

import mx.ipn.escom.web.eventos.domain.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    Optional<Equipo> findByNombreEquipo(String nombreEquipo);

    boolean existsByNombreEquipo(String nombreEquipo);
}
