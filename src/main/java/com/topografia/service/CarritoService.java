package com.topografia.service;

import com.topografia.model.Carrito;
import com.topografia.model.CarritoItem;
import com.topografia.model.Servicio;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CarritoService {
    
    private static final String CARRITO_SESSION_KEY = "carrito";
    
    private final ContenidoService contenidoService;
    
    public CarritoService(ContenidoService contenidoService) {
        this.contenidoService = contenidoService;
    }
    
    private Carrito getCarritoFromSession(HttpSession session) {
        Carrito carrito = (Carrito) session.getAttribute(CARRITO_SESSION_KEY);
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute(CARRITO_SESSION_KEY, carrito);
        }
        return carrito;
    }
    
    public void agregarAlCarrito(Long idServicio, HttpSession session) {
        Servicio servicio = contenidoService.listarServicios().stream()
                .filter(s -> s.getIdServicio().equals(idServicio))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        
        Carrito carrito = getCarritoFromSession(session);
        carrito.agregarItem(new CarritoItem(servicio));
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }
    
    public void eliminarDelCarrito(Long idServicio, HttpSession session) {
        Carrito carrito = getCarritoFromSession(session);
        carrito.eliminarItem(idServicio);
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }
    
    public void actualizarCantidad(Long idServicio, Integer cantidad, HttpSession session) {
        Carrito carrito = getCarritoFromSession(session);
        carrito.actualizarCantidad(idServicio, cantidad);
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }
    
    public Carrito obtenerCarrito(HttpSession session) {
        return getCarritoFromSession(session);
    }
    
    public void vaciarCarrito(HttpSession session) {
        session.removeAttribute(CARRITO_SESSION_KEY);
    }
    
    public Integer getCantidadItems(HttpSession session) {
        return getCarritoFromSession(session).getCantidadTotal();
    }
}