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

/**
 *
 * @author bsoli
 */
@Controller
public class HomeController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        model.addAttribute("noticias", contenidoService.listarNoticias());
        return "index";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }
}