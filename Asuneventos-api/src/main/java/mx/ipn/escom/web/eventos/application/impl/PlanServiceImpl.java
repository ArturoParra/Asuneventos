package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.PlanService;
import mx.ipn.escom.web.eventos.domain.entities.Plan;
import mx.ipn.escom.web.eventos.domain.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Plan> findAll() {
        return planRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }

    @Override
    @Transactional
    public Plan save(Plan plan) {
        return planRepository.save(plan);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        planRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Plan> findByServicioId(Long idServicio) {
        return planRepository.findByServicio_IdServicio(idServicio);
    }
}
