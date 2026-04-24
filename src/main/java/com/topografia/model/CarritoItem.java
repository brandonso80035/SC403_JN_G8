/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author bsoli
 */

public class CarritoItem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String imagenUrl;
    private Integer cantidad;
    
    public CarritoItem() {
        this.cantidad = 1;
    }
    
    public CarritoItem(Servicio servicio) {
        this.idServicio = servicio.getIdServicio();
        this.nombre = servicio.getNombre();
        this.descripcion = servicio.getDescripcion();
        this.precio = servicio.getPrecioDesde();
        this.imagenUrl = servicio.getImagenUrl();
        this.cantidad = 1;
    }
    
    // Getters y Setters
    public Long getIdServicio() { return idServicio; }
    public void setIdServicio(Long idServicio) { this.idServicio = idServicio; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    
    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    
    public Double getSubtotal() {
        return precio != null ? precio * cantidad : 0.0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarritoItem that = (CarritoItem) o;
        return Objects.equals(idServicio, that.idServicio);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(idServicio);
    }
}