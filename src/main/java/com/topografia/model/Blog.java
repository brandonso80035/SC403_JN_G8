/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.model;

import java.time.LocalDate;

/**
 *
 * @author bsoli
 */
public class Blog {
    private Long idBlog;
    private String titulo;
    private String contenido;
    private String categoria;
    private LocalDate fecha;

    public Blog() {}
    public Long getIdBlog() { return idBlog; }
    public void setIdBlog(Long idBlog) { this.idBlog = idBlog; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
}
