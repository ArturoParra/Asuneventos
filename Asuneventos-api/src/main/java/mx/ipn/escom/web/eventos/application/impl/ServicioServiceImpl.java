package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.ServicioService;
import mx.ipn.escom.web.eventos.domain.entities.Servicio;
import mx.ipn.escom.web.eventos.domain.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
    }

    @Override
    @Transactional
    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        servicioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> findByNombreContaining(String nombre) {
        return servicioRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
