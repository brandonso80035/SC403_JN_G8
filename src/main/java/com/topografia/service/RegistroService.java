/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.service;

import com.topografia.model.Rol;
import com.topografia.model.Usuario;
import com.topografia.repository.RolRepository;
import com.topografia.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author bsoli
 */


@Service
public class RegistroService {
    
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    
    // Almacenamiento temporal de tokens (en producción usar Redis o base de datos)
    private final Map<String, TokenRecuperacion> tokens = new HashMap<>();
    
    public RegistroService(UsuarioRepository usuarioRepository, 
                           RolRepository rolRepository,
                           PasswordEncoder passwordEncoder,
                           EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }
    
    @Transactional
    public void registrarUsuario(Usuario usuario) {
        // Encriptar contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivo(true);
        
        // Asignar rol USER por defecto
        Rol rolUser = rolRepository.findByRol("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
        usuario.getRoles().add(rolUser);
        
        usuarioRepository.save(usuario);
    }
    
    public boolean existeUsuario(String username, String email) {
        return usuarioRepository.findByUsernameOrCorreo(username, email).isPresent();
    }
    
    @Transactional
    public void generarTokenRecuperacion(String username, String email) {
        Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!usuario.getCorreo().equals(email)) {
            throw new RuntimeException("El correo no coincide con el usuario");
        }
        
        String token = UUID.randomUUID().toString();
        TokenRecuperacion tokenRec = new TokenRecuperacion();
        tokenRec.setToken(token);
        tokenRec.setUsername(username);
        tokenRec.setExpiracion(LocalDateTime.now().plusHours(1));
        
        tokens.put(token, tokenRec);
        
        // Lo usamos a modo de enlace
        String resetUrl = "http://localhost:8081/registro/reset-password?token=" + token;
        System.out.println("=== ENLACE DE RECUPERACIÓN ===");
        System.out.println(resetUrl);
        System.out.println("==============================");
    }
    
    public boolean validarToken(String token) {
        TokenRecuperacion tokenRec = tokens.get(token);
        if (tokenRec == null) {
            return false;
        }
        return tokenRec.getExpiracion().isAfter(LocalDateTime.now());
    }
    
    @Transactional
    public void cambiarPassword(String token, String nuevaPassword) {
        TokenRecuperacion tokenRec = tokens.get(token);
        if (tokenRec == null || tokenRec.getExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido o expirado");
        }
        
        Usuario usuario = usuarioRepository.findByUsernameAndActivoTrue(tokenRec.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(usuario);
        
        // Eliminar token usado
        tokens.remove(token);
    }
    
    // Clase interna para tokens
    private static class TokenRecuperacion {
        private String token;
        private String username;
        private LocalDateTime expiracion;
        
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public LocalDateTime getExpiracion() { return expiracion; }
        public void setExpiracion(LocalDateTime expiracion) { this.expiracion = expiracion; }
    }
}