/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.controller;

import com.topografia.service.RegistroService;
import com.topografia.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author bsoli
 */


@Controller
@RequestMapping("/registro")
public class RegistroController {
    
    private final RegistroService registroService;
    
    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }
    
    @PostMapping("/crear")
    public String registrarUsuario(Usuario usuario, 
                                   @RequestParam("confirmarPassword") String confirmarPassword,
                                   Model model) {
        // Validar que las contraseñas coincidan
        if (!usuario.getPassword().equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }
        
        // Validar que el usuario no exista
        if (registroService.existeUsuario(usuario.getUsername(), usuario.getCorreo())) {
            model.addAttribute("error", "El nombre de usuario o correo ya está registrado");
            return "registro";
        }
        
        try {
            registroService.registrarUsuario(usuario);
            model.addAttribute("exito", "Usuario registrado exitosamente. Ahora puedes iniciar sesión.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar usuario: " + e.getMessage());
            return "registro";
        }
    }
    
    @GetMapping("/recordar")
    public String mostrarFormularioRecordar() {
        return "recordar-password";
    }
    
    @PostMapping("/recordar")
    public String recordarPassword(@RequestParam("username") String username,
                                   @RequestParam("email") String email,
                                   Model model) {
        try {
            registroService.generarTokenRecuperacion(username, email);
            model.addAttribute("exito", "Se ha enviado un enlace de recuperación a tu correo electrónico");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "recordar-password";
        }
    }
    
    @GetMapping("/reset-password")
    public String mostrarFormularioReset(@RequestParam("token") String token, Model model) {
        if (registroService.validarToken(token)) {
            model.addAttribute("token", token);
            return "reset-password";
        } else {
            model.addAttribute("error", "El enlace de recuperación es inválido o ha expirado");
            return "login";
        }
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String password,
                                @RequestParam("confirmarPassword") String confirmarPassword,
                                Model model) {
        if (!password.equals(confirmarPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            model.addAttribute("token", token);
            return "reset-password";
        }
        
        try {
            registroService.cambiarPassword(token, password);
            model.addAttribute("exito", "Contraseña actualizada exitosamente. Ahora puedes iniciar sesión.");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-password";
        }
    }
}