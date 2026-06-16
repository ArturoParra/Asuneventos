package mx.ipn.escom.web.eventos.application.dto.response;

import java.time.LocalDate;

public class PlanResponse {

    private Long idPlan;
    private ServicioResponse servicio;
    private LocalDate fecha;
    private String tituloSerie;

    public PlanResponse() {}

    public PlanResponse(Long idPlan, ServicioResponse servicio, LocalDate fecha, String tituloSerie) {
        this.idPlan = idPlan;
        this.servicio = servicio;
        this.fecha = fecha;
        this.tituloSerie = tituloSerie;
    }

    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public ServicioResponse getServicio() { return servicio; }
    public void setServicio(ServicioResponse servicio) { this.servicio = servicio; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTituloSerie() { return tituloSerie; }
    public void setTituloSerie(String tituloSerie) { this.tituloSerie = tituloSerie; }
}
