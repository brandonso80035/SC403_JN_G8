package com.topografia.controller;

import com.topografia.service.CarritoService;
import com.topografia.service.ContenidoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {
    
    private final ContenidoService contenidoService;
    private final CarritoService carritoService;
    
    public ServiciosController(ContenidoService contenidoService, CarritoService carritoService) {
        this.contenidoService = contenidoService;
        this.carritoService = carritoService;
    }
    
    @GetMapping
    public String listar(Model model, HttpSession session) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "servicios/lista";
    }
}