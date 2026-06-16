package mx.ipn.escom.web.eventos.application.dto.response;

public class AsignacionResponse {

    private Long idAsignacion;
    private PersonaResponse persona;
    private PlanResponse plan;
    private EquipoResponse equipo;
    private String estado;

    public AsignacionResponse() {}

    public AsignacionResponse(Long idAsignacion, PersonaResponse persona, PlanResponse plan,
                              EquipoResponse equipo, String estado) {
        this.idAsignacion = idAsignacion;
        this.persona = persona;
        this.plan = plan;
        this.equipo = equipo;
        this.estado = estado;
    }

    public Long getIdAsignacion() { return idAsignacion; }
    public void setIdAsignacion(Long idAsignacion) { this.idAsignacion = idAsignacion; }
    public PersonaResponse getPersona() { return persona; }
    public void setPersona(PersonaResponse persona) { this.persona = persona; }
    public PlanResponse getPlan() { return plan; }
    public void setPlan(PlanResponse plan) { this.plan = plan; }
    public EquipoResponse getEquipo() { return equipo; }
    public void setEquipo(EquipoResponse equipo) { this.equipo = equipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
