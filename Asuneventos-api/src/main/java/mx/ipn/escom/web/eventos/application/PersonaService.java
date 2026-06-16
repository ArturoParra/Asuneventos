package mx.ipn.escom.web.eventos.application;

import mx.ipn.escom.web.eventos.domain.entities.Persona;

import java.util.List;
import java.util.Optional;

public interface PersonaService {

    List<Persona> findAll();

    Optional<Persona> findById(Long id);

    Persona save(Persona persona);

    void deleteById(Long id);

    Optional<Persona> findByEmail(String email);

    boolean existsByEmail(String email);
}
