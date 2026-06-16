package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Servicio;

import java.util.List;
import java.util.Optional;

public interface ServicioService {

    List<Servicio> findAll();

    Optional<Servicio> findById(Long id);

    Servicio save(Servicio servicio);

    void deleteById(Long id);

    List<Servicio> findByNombreContaining(String nombre);
}
