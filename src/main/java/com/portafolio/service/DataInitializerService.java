package com.portafolio.service;

import com.portafolio.entity.Perfil;
import com.portafolio.entity.Usuario;
import com.portafolio.repository.PerfilRepository;
import com.portafolio.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Crea el usuario administrador por defecto (y su perfil vacio) la primera
 * vez que se levanta la aplicacion, si todavia no existe ninguno.
 * IMPORTANTE: esto NO crea semanas ni actividades de ejemplo; esos datos
 * siempre provienen exclusivamente de lo que el administrador registre.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DataInitializerService implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.default-username}")
    private String defaultUsername;

    @Value("${app.admin.default-password}")
    private String defaultPassword;

    @Value("${app.admin.default-email}")
    private String defaultEmail;

    @Value("${app.docente.default-username}")
    private String docenteUsername;

    @Value("${app.docente.default-password}")
    private String docentePassword;

    @Value("${app.docente.default-email}")
    private String docenteEmail;

    @Override
    public void run(String... args) {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername(defaultUsername);
            admin.setPassword(passwordEncoder.encode(defaultPassword));
            admin.setEmail(defaultEmail);
            admin.setHabilitado(true);
            admin.setRol("ROLE_ADMIN");
            usuarioRepository.save(admin);

            Perfil perfil = new Perfil();
            perfil.setNombres("Mi");
            perfil.setApellidos("Nombre");
            perfil.setCarrera("Ingenieria de Sistemas");
            perfil.setDescripcion("Bienvenida a mi portafolio academico de Java.");
            perfil.setCorreo(defaultEmail);
            perfil.setUsuario(admin);
            perfilRepository.save(perfil);

            Usuario docente = new Usuario();
            docente.setUsername(docenteUsername);
            docente.setPassword(passwordEncoder.encode(docentePassword));
            docente.setEmail(docenteEmail);
            docente.setHabilitado(true);
            docente.setRol("ROLE_DOCENTE");
            usuarioRepository.save(docente);

            log.info("=========================================================");
            log.info(" Usuario administrador creado automaticamente");
            log.info(" Usuario: {}", defaultUsername);
            log.info(" Password: {}", defaultPassword);
            log.info(" (cambia esta contrasena luego desde Perfil / BD)");
            log.info("---------------------------------------------------------");
            log.info(" Usuario docente (solo lectura) creado automaticamente");
            log.info(" Usuario: {}", docenteUsername);
            log.info(" Password: {}", docentePassword);
            log.info("=========================================================");
        }
    }
}
