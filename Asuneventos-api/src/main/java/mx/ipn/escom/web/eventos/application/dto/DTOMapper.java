package mx.ipn.escom.web.eventos.application.dto;

import mx.ipn.escom.web.eventos.application.dto.request.*;
import mx.ipn.escom.web.eventos.application.dto.response.*;
import mx.ipn.escom.web.eventos.domain.entities.*;

public class DTOMapper {

    private DTOMapper() {}

    public static ServicioResponse toServicioResponse(Servicio s) {
        if (s == null) return null;
        return new ServicioResponse(s.getIdServicio(), s.getNombre(), s.getDescripcion());
    }

    public static Servicio toServicio(ServicioRequest req) {
        if (req == null) return null;
        Servicio s = new Servicio();
        s.setNombre(req.getNombre());
        s.setDescripcion(req.getDescripcion());
        return s;
    }

    public static PlanResponse toPlanResponse(Plan p) {
        if (p == null) return null;
        return new PlanResponse(p.getIdPlan(), toServicioResponse(p.getServicio()),
                p.getFecha(), p.getTituloSerie());
    }

    public static Plan toPlan(PlanRequest req, Servicio servicio) {
        if (req == null) return null;
        Plan p = new Plan();
        p.setServicio(servicio);
        p.setFecha(req.getFecha());
        p.setTituloSerie(req.getTituloSerie());
        return p;
    }

    public static ElementoResponse toElementoResponse(Elemento e) {
        if (e == null) return null;
        return new ElementoResponse(e.getIdElemento(), toPlanResponse(e.getPlan()),
                e.getOrden(), e.getTitulo(), e.getDuracion());
    }

    public static Elemento toElemento(ElementoRequest req, Plan plan) {
        if (req == null) return null;
        Elemento e = new Elemento();
        e.setPlan(plan);
        e.setOrden(req.getOrden());
        e.setTitulo(req.getTitulo());
        e.setDuracion(req.getDuracion());
        return e;
    }

    public static PersonaResponse toPersonaResponse(Persona p) {
        if (p == null) return null;
        return new PersonaResponse(p.getIdPersona(), p.getNombre(), p.getEmail(), p.getRol());
    }

    public static Persona toPersona(PersonaRequest req) {
        if (req == null) return null;
        Persona p = new Persona();
        p.setNombre(req.getNombre());
        p.setEmail(req.getEmail());
        p.setPassword(req.getPassword());
        p.setRol(req.getRol() != null ? req.getRol() : "ROLE_VOLUNTARIO");
        return p;
    }

    public static EquipoResponse toEquipoResponse(Equipo e) {
        if (e == null) return null;
        return new EquipoResponse(e.getIdEquipo(), e.getNombreEquipo());
    }

    public static Equipo toEquipo(EquipoRequest req) {
        if (req == null) return null;
        Equipo e = new Equipo();
        e.setNombreEquipo(req.getNombreEquipo());
        return e;
    }

    public static AsignacionResponse toAsignacionResponse(Asignacion a) {
        if (a == null) return null;
        return new AsignacionResponse(a.getIdAsignacion(),
                toPersonaResponse(a.getPersona()),
                toPlanResponse(a.getPlan()),
                toEquipoResponse(a.getEquipo()),
                a.getEstado());
    }

    public static Asignacion toAsignacion(AsignacionRequest req, Persona persona,
                                          Plan plan, Equipo equipo) {
        if (req == null) return null;
        Asignacion a = new Asignacion();
        a.setPersona(persona);
        a.setPlan(plan);
        a.setEquipo(equipo);
        a.setEstado(req.getEstado() != null ? req.getEstado() : "Pendiente");
        return a;
    }
}
