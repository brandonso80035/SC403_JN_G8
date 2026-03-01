/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.controller;

import com.topografia.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author bsoli
 */
@Controller
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        return "servicios/lista";
    }

    @GetMapping("/levantamientos")
    public String levantamientos() { return "servicios/levantamientos"; }

    @GetMapping("/curvas")
    public String curvas() { return "servicios/curvas"; }

    @GetMapping("/replanteo")
    public String replanteo() { return "servicios/replanteo"; }

    @GetMapping("/catastros")
    public String catastros() { return "servicios/catastros"; }

    @GetMapping("/permisos")
    public String permisos() { return "servicios/permisos"; }

    @GetMapping("/arquitectura")
    public String arquitectura() { return "servicios/arquitectura"; }

    @GetMapping("/consultorias")
    public String consultorias() { return "servicios/consultorias"; }

    @GetMapping("/bonos")
    public String bonos() { return "servicios/bonos"; }
}
