package mx.ipn.escom.web.eventos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(DotenvEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Path envFile = Paths.get(".env");
        log.info("Buscando .env en: {}", envFile.toAbsolutePath());
        if (Files.exists(envFile)) {
            log.info("Archivo .env encontrado, cargando variables...");
            Map<String, Object> envVars = new HashMap<>();
            try {
                for (String line : Files.readAllLines(envFile)) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int eq = line.indexOf('=');
                    if (eq > 0) {
                        String key = line.substring(0, eq).trim();
                        String value = line.substring(eq + 1).trim();
                        envVars.put(key, value);
                    }
                }
                environment.getPropertySources().addFirst(new MapPropertySource("dotenv", envVars));
                log.info("Variables cargadas del .env: {}", envVars.keySet());
            } catch (IOException e) {
                log.error("Error al leer .env", e);
            }
        } else {
            log.warn("Archivo .env no encontrado en: {}", envFile.toAbsolutePath());
        }
    }
}
