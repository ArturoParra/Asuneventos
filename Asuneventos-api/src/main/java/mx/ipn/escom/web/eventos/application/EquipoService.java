package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Equipo;

import java.util.List;
import java.util.Optional;

public interface EquipoService {

    List<Equipo> findAll();

    Optional<Equipo> findById(Long id);

    Equipo save(Equipo equipo);

    void deleteById(Long id);

    Optional<Equipo> findByNombreEquipo(String nombreEquipo);

    boolean existsByNombreEquipo(String nombreEquipo);
}
