package mx.ipn.escom.web.eventos.application.impl;

import mx.ipn.escom.web.eventos.application.AsignacionService;
import mx.ipn.escom.web.eventos.domain.entities.Asignacion;
import mx.ipn.escom.web.eventos.domain.repository.AsignacionRepository;
import mx.ipn.escom.web.eventos.infrastructure.MailService;
import mx.ipn.escom.web.eventos.infrastructure.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Service
public class AsignacionServiceImpl implements AsignacionService {

    @Autowired
    private AsignacionRepository asignacionRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PdfGenerationService pdfGenerationService;

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
        Optional<Asignacion> existente = asignacionRepository.findByPersona_IdPersonaAndPlan_IdPlanAndEquipo_IdEquipo(
                asignacion.getPersona().getIdPersona(),
                asignacion.getPlan().getIdPlan(),
                asignacion.getEquipo().getIdEquipo()
        );

        if (existente.isPresent()) {
            Long existingId = existente.get().getIdAsignacion();
            if (asignacion.getIdAsignacion() == null || !existingId.equals(asignacion.getIdAsignacion())) {
                throw new IllegalArgumentException("La asignación para esta persona, plan y equipo ya existe.");
            }
        }

        Asignacion saved = asignacionRepository.save(asignacion);

        if ("Pendiente".equalsIgnoreCase(saved.getEstado()) && saved.getPersona() != null) {
            String email = saved.getPersona().getEmail();
            String equipo = saved.getEquipo() != null ? saved.getEquipo().getNombreEquipo() : "Sin equipo";
            String planTitulo = saved.getPlan() != null ? saved.getPlan().getTituloSerie() : "Sin plan";

            String subject = "Nueva asignacion: " + equipo;
            String text = "Hola " + saved.getPersona().getNombre() + ",\n\n"
                    + "Se te ha asignado al equipo \"" + equipo + "\" "
                    + "para el plan \"" + planTitulo + "\".\n"
                    + "Por favor, revisa el archivo PDF adjunto con los detalles de tu asignación.\n\n"
                    + "Asuneventos - IPN ESCOM";

            try {
                Context context = new Context();
                context.setVariable("nombre", saved.getPersona().getNombre());
                context.setVariable("equipo", equipo);
                context.setVariable("plan", planTitulo);
                context.setVariable("estado", saved.getEstado());

                String htmlContent = templateEngine.process("asignacion-pdf", context);
                byte[] pdfBytes = pdfGenerationService.generatePdfFromHtml(htmlContent);

                mailService.sendNotificacionAsignacionConPdf(email, subject, text, pdfBytes, "Detalles_Asignacion.pdf");
            } catch (Exception e) {
                System.out.println("Error al enviar correo de notificacion con PDF: " + e.getMessage());
                e.printStackTrace();
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
