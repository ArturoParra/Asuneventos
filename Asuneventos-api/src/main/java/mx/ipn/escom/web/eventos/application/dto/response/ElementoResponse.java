package mx.ipn.escom.web.eventos.application.dto.response;

public class ElementoResponse {

    private Long idElemento;
    private PlanResponse plan;
    private Integer orden;
    private String titulo;
    private Integer duracion;

    public ElementoResponse() {}

    public ElementoResponse(Long idElemento, PlanResponse plan, Integer orden, String titulo, Integer duracion) {
        this.idElemento = idElemento;
        this.plan = plan;
        this.orden = orden;
        this.titulo = titulo;
        this.duracion = duracion;
    }

    public Long getIdElemento() { return idElemento; }
    public void setIdElemento(Long idElemento) { this.idElemento = idElemento; }
    public PlanResponse getPlan() { return plan; }
    public void setPlan(PlanResponse plan) { this.plan = plan; }
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
}
