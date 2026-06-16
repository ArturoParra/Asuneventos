package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para solicitud de inicio de sesion")
public class LoginRequest {

    @Schema(description = "Email del usuario registrado", example = "usuario@ejemplo.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Contrasena del usuario", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
