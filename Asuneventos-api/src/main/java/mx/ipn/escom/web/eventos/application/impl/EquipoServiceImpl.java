package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.EquipoService;
import mx.ipn.escom.web.eventos.domain.entities.Equipo;
import mx.ipn.escom.web.eventos.domain.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private EquipoRepository equipoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipo> findById(Long id) {
        return equipoRepository.findById(id);
    }

    @Override
    @Transactional
    public Equipo save(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        equipoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Equipo> findByNombreEquipo(String nombreEquipo) {
        return equipoRepository.findByNombreEquipo(nombreEquipo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByNombreEquipo(String nombreEquipo) {
        return equipoRepository.existsByNombreEquipo(nombreEquipo);
    }
}
