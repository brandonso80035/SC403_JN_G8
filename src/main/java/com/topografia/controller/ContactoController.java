/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.controller;

import com.topografia.service.ContactoService;
import com.topografia.service.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author bsoli
 */
@Controller
@RequestMapping("/contacto")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private ContenidoService contenidoService;

    @GetMapping
    public String mostrar(Model model) {
        model.addAttribute("servicios", contenidoService.listarServicios());
        return "contacto/formulario";
    }

    @PostMapping("/enviar")
    public String enviar(@RequestParam String nombre,
                         @RequestParam String telefono,
                         @RequestParam String email,
                         @RequestParam String servicio,
                         @RequestParam String mensaje,
                         RedirectAttributes redirectAttrs) {
        try {
            contactoService.registrarContacto(nombre, telefono, email, servicio, mensaje);
            redirectAttrs.addFlashAttribute("mensaje", 
                "¡Mensaje enviado! Nos pondremos en contacto pronto.");
        } catch (IllegalArgumentException e) {
            redirectAttrs.addFlashAttribute("error", e.getMessage());
            return "redirect:/contacto";
        }
        return "redirect:/contacto";
    }

    @GetMapping("/mensajes")
    public String listarMensajes(Model model) {
        model.addAttribute("contactos", contactoService.listarContactos());
        return "contacto/mensajes";
    }
}