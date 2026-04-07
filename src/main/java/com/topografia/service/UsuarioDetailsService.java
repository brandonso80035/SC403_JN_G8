package com.topografia.service;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.topografia.model.Rol;
import com.topografia.model.Usuario;
import com.topografia.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 * @author bsoli
 */

@Service("userDetailsService")
public class UsuarioDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioDetailsService.class);
    
    private final UsuarioRepository usuarioRepository;
    private final HttpSession session;

    public UsuarioDetailsService(UsuarioRepository usuarioRepository, HttpSession session) {
        this.usuarioRepository = usuarioRepository;
        this.session = session;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Intentando cargar usuario: {}", username);
        
        Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(username)
                .orElseThrow(() -> {
                    logger.error("Usuario no encontrado: {}", username);
                    return new UsernameNotFoundException("Usuario no encontrado: " + username);
                });

        logger.info("Usuario encontrado: {}, Roles: {}", usuario.getUsername(), 
                    usuario.getRoles() != null ? usuario.getRoles().size() : 0);
        
        // Guardar información en sesión
        try {
            session.removeAttribute("imagenUsuario");
            if (usuario.getRutaImagen() != null && !usuario.getRutaImagen().isEmpty()) {
                session.setAttribute("imagenUsuario", usuario.getRutaImagen());
                logger.debug("Imagen de usuario guardada en sesión");
            }
            
            String nombreCompleto = (usuario.getNombre() != null ? usuario.getNombre() : "") + " " + 
                                   (usuario.getApellidos() != null ? usuario.getApellidos() : "");
            session.setAttribute("nombreUsuario", nombreCompleto.trim());
            logger.debug("Nombre de usuario guardado en sesión: {}", nombreCompleto);
        } catch (Exception e) {
            logger.warn("Error al guardar atributos en sesión: {}", e.getMessage());
        }

        // Cargar roles (manejar caso nulo)
        Set<SimpleGrantedAuthority> roles = new HashSet<>();
        if (usuario.getRoles() != null && !usuario.getRoles().isEmpty()) {
            roles = usuario.getRoles().stream()
                    .map(rol -> {
                        String roleName = rol.getRol();
                        logger.debug("Asignando rol: ROLE_{}", roleName);
                        return new SimpleGrantedAuthority("ROLE_" + roleName);
                    })
                    .collect(Collectors.toSet());
        } else {
            // Rol por defecto si no tiene ninguno
            logger.warn("Usuario {} no tiene roles asignados, asignando rol USER por defecto", username);
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        logger.info("Usuario {} autenticado con {} roles", username, roles.size());
        
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}