package mx.ipn.escom.web.eventos.application.dto.response;

public class ServicioResponse {

    private Long idServicio;
    private String nombre;
    private String descripcion;

    public ServicioResponse() {}

    public ServicioResponse(Long idServicio, String nombre, String descripcion) {
        this.idServicio = idServicio;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
