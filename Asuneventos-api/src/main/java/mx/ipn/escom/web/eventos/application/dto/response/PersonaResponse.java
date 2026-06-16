package mx.ipn.escom.web.eventos.application.dto.response;

public class PersonaResponse {

    private Long idPersona;
    private String nombre;
    private String email;
    private String rol;

    public PersonaResponse() {}

    public PersonaResponse(Long idPersona, String nombre, String email, String rol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
