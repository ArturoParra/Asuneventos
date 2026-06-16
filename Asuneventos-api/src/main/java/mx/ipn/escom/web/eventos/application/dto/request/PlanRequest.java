package mx.ipn.escom.web.eventos.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class PlanRequest {

    @NotNull
    private Long idServicio;

    @NotNull
    private LocalDate fecha;

    @NotBlank
    private String tituloSerie;

    public PlanRequest() {}

    public PlanRequest(Long idServicio, LocalDate fecha, String tituloSerie) {
        this.idServicio = idServicio;
        this.fecha = fecha;
        this.tituloSerie = tituloSerie;
    }

    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getTituloSerie() { return tituloSerie; }
    public void setTituloSerie(String tituloSerie) { this.tituloSerie = tituloSerie; }
}
