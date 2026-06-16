package mx.ipn.escom.web.eventos.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public class EquipoRequest {

    @NotBlank
    private String nombreEquipo;

    public EquipoRequest() {}

    public EquipoRequest(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreEquipo() { return nombreEquipo; }
    public void setNombreEquipo(String nombreEquipo) { this.nombreEquipo = nombreEquipo; }
}
