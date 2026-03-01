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
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("entradas", contenidoService.listarBlog());
        return "blog/lista";
    }
}