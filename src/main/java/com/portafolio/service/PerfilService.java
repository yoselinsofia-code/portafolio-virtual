package com.portafolio.service;

import com.portafolio.entity.Perfil;
import com.portafolio.entity.Usuario;
import com.portafolio.repository.PerfilRepository;
import com.portafolio.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final FileStorageService fileStorageService;

    /** El sistema esta pensado para un unico administrador/portafolio. */
    public Perfil obtenerPerfilPrincipal() {
        return perfilRepository.findFirstByOrderByIdAsc().orElse(new Perfil());
    }

    @Transactional
    public Perfil guardar(Perfil perfil, MultipartFile foto, String username) {
        Perfil existente = perfilRepository.findFirstByOrderByIdAsc().orElse(null);

        if (existente != null) {
            perfil.setId(existente.getId());
            perfil.setUsuario(existente.getUsuario());
            if (foto == null || foto.isEmpty()) {
                perfil.setFotoPerfil(existente.getFotoPerfil());
            }
        }

        if (perfil.getUsuario() == null) {
            Usuario usuario = usuarioRepository.findByUsername(username).orElse(null);
            perfil.setUsuario(usuario);
        }

        if (foto != null && !foto.isEmpty()) {
            perfil.setFotoPerfil(fileStorageService.store(foto, "perfil"));
        }

        return perfilRepository.save(perfil);
    }
}
