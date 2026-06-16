package mx.ipn.escom.web.eventos.application.dto.request;

import jakarta.validation.constraints.NotNull;

public class AsignacionRequest {

    @NotNull
    private Long idPersona;

    @NotNull
    private Long idPlan;

    @NotNull
    private Long idEquipo;

    private String estado;

    public AsignacionRequest() {}

    public AsignacionRequest(Long idPersona, Long idPlan, Long idEquipo, String estado) {
        this.idPersona = idPersona;
        this.idPlan = idPlan;
        this.idEquipo = idEquipo;
        this.estado = estado;
    }

    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
