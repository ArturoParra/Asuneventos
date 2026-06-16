package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.PersonaService;
import mx.ipn.escom.web.eventos.domain.entities.Persona;
import mx.ipn.escom.web.eventos.domain.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> findAll() {
        return personaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findById(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    @Transactional
    public Persona save(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> findByEmail(String email) {
        return personaRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return personaRepository.existsByEmail(email);
    }
}
