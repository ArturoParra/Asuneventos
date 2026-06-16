package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.ElementoService;
import mx.ipn.escom.web.eventos.domain.entities.Elemento;
import mx.ipn.escom.web.eventos.domain.repository.ElementoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ElementoServiceImpl implements ElementoService {

    @Autowired
    private ElementoRepository elementoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Elemento> findAll() {
        return elementoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Elemento> findById(Long id) {
        return elementoRepository.findById(id);
    }

    @Override
    @Transactional
    public Elemento save(Elemento elemento) {
        return elementoRepository.save(elemento);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        elementoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Elemento> findByPlanIdOrderByOrden(Long idPlan) {
        return elementoRepository.findByPlan_IdPlanOrderByOrdenAsc(idPlan);
    }
}
