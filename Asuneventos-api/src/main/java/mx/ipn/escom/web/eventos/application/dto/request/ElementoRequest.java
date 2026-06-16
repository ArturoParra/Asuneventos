package mx.ipn.escom.web.eventos.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ElementoRequest {

    @NotNull
    private Long idPlan;

    @NotNull
    private Integer orden;

    @NotBlank
    private String titulo;

    @NotNull
    private Integer duracion;

    public ElementoRequest() {}

    public ElementoRequest(Long idPlan, Integer orden, String titulo, Integer duracion) {
        this.idPlan = idPlan;
        this.orden = orden;
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
}
