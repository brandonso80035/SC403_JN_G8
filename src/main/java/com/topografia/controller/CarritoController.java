package com.topografia.controller;

import com.topografia.model.Carrito;
import com.topografia.service.CarritoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/carrito")
public class CarritoController {
    
    private final CarritoService carritoService;
    
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }
    
    @GetMapping
    public String verCarrito(Model model, HttpSession session) {
        Carrito carrito = carritoService.obtenerCarrito(session);
        model.addAttribute("carrito", carrito);
        model.addAttribute("cantidadCarrito", carritoService.getCantidadItems(session));
        return "carrito/ver";
    }
    
    @PostMapping("/agregar/{idServicio}")
    public String agregarAlCarrito(@PathVariable Long idServicio, HttpSession session) {
        carritoService.agregarAlCarrito(idServicio, session);
        return "redirect:/servicios";
    }
    
    @GetMapping("/eliminar/{idServicio}")
    public String eliminarDelCarrito(@PathVariable Long idServicio, HttpSession session) {
        carritoService.eliminarDelCarrito(idServicio, session);
        return "redirect:/carrito";
    }
    
    @PostMapping("/actualizar")
    public String actualizarCantidad(@RequestParam Long idServicio, 
                                      @RequestParam Integer cantidad,
                                      HttpSession session) {
        carritoService.actualizarCantidad(idServicio, cantidad, session);
        return "redirect:/carrito";
    }
    
    @GetMapping("/vaciar")
    public String vaciarCarrito(HttpSession session) {
        carritoService.vaciarCarrito(session);
        return "redirect:/carrito";
    }
}