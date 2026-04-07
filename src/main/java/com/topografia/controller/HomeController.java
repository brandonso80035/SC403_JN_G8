package com.topografia.controller;

import com.topografia.service.ContenidoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    private final ContenidoService contenidoService;
    
    public HomeController(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }
    
    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        model.addAttribute("proyectos", contenidoService.listarProyectos());
        model.addAttribute("noticias", contenidoService.listarNoticias());
        model.addAttribute("blog", contenidoService.listarBlog());
        
        // Verificar roles para mostrar opciones en el menú
        if (authentication != null && authentication.isAuthenticated()) {
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            boolean isEditor = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_EDITOR"));
            
            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isEditor", isEditor);
            model.addAttribute("username", authentication.getName());
        }
        
        return "index";
    }
  
}