package mx.ipn.escom.web.eventos.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.ipn.escom.web.eventos.application.PersonaService;
import mx.ipn.escom.web.eventos.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacion", description = "Login y generacion de tokens JWT")
public class AuthController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Iniciar sesion", description = "Autentica al usuario por email y contrasena, devuelve un token JWT")
    @ApiResponse(responseCode = "200", description = "Login exitoso, devuelve el token JWT")
    @ApiResponse(responseCode = "401", description = "Credenciales invalidas")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Credenciales de acceso",
                required = true,
                content = @io.swagger.v3.oas.annotations.media.Content(
                    schema = @Schema(implementation = LoginRequest.class)
                )
            )
            @RequestBody LoginRequest request) {
        return personaService.findByEmail(request.getEmail())
                .filter(persona -> passwordEncoder.matches(request.getPassword(), persona.getPassword()))
                .map(persona -> {
                    String token = jwtService.generateToken(persona.getEmail(), persona.getRol());
                    return ResponseEntity.ok(Map.of(
                            "token", token,
                            "email", persona.getEmail(),
                            "rol", persona.getRol()
                    ));
                })
                .orElse(ResponseEntity.status(401).body(Map.of("error", "Credenciales invalidas")));
    }
}
