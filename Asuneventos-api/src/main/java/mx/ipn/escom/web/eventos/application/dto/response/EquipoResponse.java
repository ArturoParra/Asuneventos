package mx.ipn.escom.web.eventos.application.dto.response;

public class EquipoResponse {

    private Long idEquipo;
    private String nombreEquipo;

    public EquipoResponse() {}

    public EquipoResponse(Long idEquipo, String nombreEquipo) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    public Long getIdEquipo() { return idEquipo; }
    public void setIdEquipo(Long idEquipo) { this.idEquipo = idEquipo; }
    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
}
