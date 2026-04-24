package com.topografia.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<CarritoItem> items;
    
    public Carrito() {
        this.items = new ArrayList<>();
    }
    
    public List<CarritoItem> getItems() {
        return items;
    }
    
    public void setItems(List<CarritoItem> items) {
        this.items = items;
    }
    
    public void agregarItem(CarritoItem nuevoItem) {
        Optional<CarritoItem> existing = items.stream()
                .filter(item -> item.getIdServicio().equals(nuevoItem.getIdServicio()))
                .findFirst();
        
        if (existing.isPresent()) {
            existing.get().setCantidad(existing.get().getCantidad() + 1);
        } else {
            items.add(nuevoItem);
        }
    }
    
    public void eliminarItem(Long idServicio) {
        items.removeIf(item -> item.getIdServicio().equals(idServicio));
    }
    
    public void actualizarCantidad(Long idServicio, Integer cantidad) {
        items.stream()
                .filter(item -> item.getIdServicio().equals(idServicio))
                .findFirst()
                .ifPresent(item -> {
                    if (cantidad > 0) {
                        item.setCantidad(cantidad);
                    } else {
                        eliminarItem(idServicio);
                    }
                });
    }
    
    public Double getTotal() {
        return items.stream()
                .mapToDouble(CarritoItem::getSubtotal)
                .sum();
    }
    
    public Integer getCantidadTotal() {
        return items.stream()
                .mapToInt(CarritoItem::getCantidad)
                .sum();
    }
    
    public void vaciar() {
        items.clear();
    }
}