package com.topografia.controller;

import com.topografia.service.CarritoService;
import com.topografia.service.ContenidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    private final ContenidoService contenidoService;
    private final CarritoService carritoService;
    
    public HomeController(ContenidoService contenidoService, CarritoService carritoService) {
        this.contenidoService = contenidoService;
        this.carritoService = carritoService;
    }
    
    @GetMapping("/")
    public String home(Model model, HttpSession session, Authentication authentication) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        model.addAttribute("proyectos", contenidoService.listarProyectos());
        model.addAttribute("noticias", contenidoService.listarNoticias());
        model.addAttribute("blog", contenidoService.listarBlog());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            model.addAttribute("isAdmin", isAdmin);
        }
        
        return "index";
    }
    
    @GetMapping("/nosotros")
    public String nosotros(Model model, HttpSession session) {
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "nosotros/nosotros";
    }
    
    @GetMapping("/proyectos")
    public String proyectos(Model model, HttpSession session) {
        model.addAttribute("proyectos", contenidoService.listarProyectos());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "proyectos/lista";
    }
    
    @GetMapping("/noticias")
    public String noticias(Model model, HttpSession session) {
        model.addAttribute("noticias", contenidoService.listarNoticias());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "noticias/lista";
    }
    
    @GetMapping("/blog")
    public String blog(Model model, HttpSession session) {
        model.addAttribute("entradas", contenidoService.listarBlog());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "blog/lista";
    }
    
    @GetMapping("/contacto")
    public String contacto(Model model, HttpSession session) {
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "contacto/contacto";
    }
    
    @GetMapping("/acceso_denegado")
    public String accesoDenegado(Model model, HttpSession session) {
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "acceso_denegado";
    }
}