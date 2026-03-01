/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.service;

import com.topografia.model.Blog;
import com.topografia.model.Noticia;
import com.topografia.model.Proyecto;
import com.topografia.model.Servicio;
import com.topografia.repository.ContenidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author bsoli
 */
@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;

    public List<Servicio> listarServicios() {
        return contenidoRepository.listarServicios();
    }

    public List<Proyecto> listarProyectos() {
        return contenidoRepository.listarProyectos();
    }

    public List<Noticia> listarNoticias() {
        return contenidoRepository.listarNoticias();
    }

    public List<Blog> listarBlog() {
        return contenidoRepository.listarBlog();
    }
}
