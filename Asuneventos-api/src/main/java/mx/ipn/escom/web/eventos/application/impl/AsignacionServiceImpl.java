package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.AsignacionService;
import mx.ipn.escom.web.eventos.domain.entities.Asignacion;
import mx.ipn.escom.web.eventos.domain.repository.AsignacionRepository;
import mx.ipn.escom.web.eventos.infrastructure.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private MailService mailService;

    @Override
    @Transactional(readOnly = true)
    public List<Asignacion> findAll() {
        return asignacionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Asignacion> findById(Long id) {
        return asignacionRepository.findById(id);
    }

    @Override
    @Transactional
    public Asignacion save(Asignacion asignacion) {
        Asignacion saved = asignacionRepository.save(asignacion);

        if ("Pendiente".equalsIgnoreCase(saved.getEstado()) && saved.getPersona() != null) {
            String email = saved.getPersona().getEmail();
            String equipo = saved.getEquipo() != null ? saved.getEquipo().getNombreEquipo() : "Sin equipo";
            String planTitulo = saved.getPlan() != null ? saved.getPlan().getTituloSerie() : "Sin plan";

            String subject = "Nueva asignacion: " + equipo;
            String text = "Hola " + saved.getPersona().getNombre() + ",\n\n"
                    + "Se te ha asignado al equipo \"" + equipo + "\" "
                    + "para el plan \"" + planTitulo + "\".\n"
                    + "Estado actual: Pendiente.\n\n"
                    + "Por favor confirma tu disponibilidad en el sistema.\n\n"
                    + "Asuneventos - IPN ESCOM";

            try {
                mailService.sendNotificacionAsignacion(email, subject, text);
            } catch (Exception e) {
                System.out.println("Error al enviar correo de notificacion: " + e.getMessage());
            }
        }

        return saved;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        asignacionRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignacion> findByPlanId(Long idPlan) {
        return asignacionRepository.findByPlan_IdPlan(idPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignacion> findByPersonaId(Long idPersona) {
        return asignacionRepository.findByPersona_IdPersona(idPersona);
    }

    @Override
    @Transactional
    public Optional<Asignacion> updateEstado(Long id, String estado) {
        return asignacionRepository.findById(id)
                .map(asignacion -> {
                    asignacion.setEstado(estado);
                    return asignacionRepository.save(asignacion);
                });
    }
}
