package mx.ipn.escom.web;

import mx.ipn.escom.web.eventos.domain.entities.Equipo;
import mx.ipn.escom.web.eventos.domain.entities.Persona;
import mx.ipn.escom.web.eventos.domain.repository.EquipoRepository;
import mx.ipn.escom.web.eventos.domain.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class AsuneventosApiApplication implements CommandLineRunner {

    @Autowired
    private EquipoRepository equipoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(AsuneventosApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (equipoRepository.count() == 0) {
            List<Equipo> equiposPorDefecto = List.of(
                    Equipo.builder().nombreEquipo("Banda").build(),
                    Equipo.builder().nombreEquipo("Audio").build(),
                    Equipo.builder().nombreEquipo("Luces").build(),
                    Equipo.builder().nombreEquipo("Produccion").build(),
                    Equipo.builder().nombreEquipo("Logistica").build(),
                    Equipo.builder().nombreEquipo("Seguridad").build()
            );
            equipoRepository.saveAll(equiposPorDefecto);
        }

        if (!personaRepository.existsByEmail("admin@asuneventos.com")) {
            personaRepository.save(Persona.builder()
                    .nombre("Administrador")
                    .email("admin@asuneventos.com")
                    .password(passwordEncoder.encode("123456"))
                    .rol("ROLE_ADMIN")
                    .build());
        }

        System.out.println("Usuarios en BD: " + personaRepository.findAll());
    }

}
