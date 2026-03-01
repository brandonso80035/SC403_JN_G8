/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.service;

import com.topografia.model.Contacto;
import com.topografia.repository.ContactoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bsoli
 */
@Service
public class ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;

    public void registrarContacto(String nombre, String telefono,
                                   String email, String servicio, String mensaje) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El correo electrónico no es válido");
        }
        if (mensaje == null || mensaje.isBlank()) {
            throw new IllegalArgumentException("El mensaje es obligatorio");
        }
        contactoRepository.registrarContacto(nombre, telefono, email, servicio, mensaje);
    }

    public List<Contacto> listarContactos() {
        return contactoRepository.listarContactos();
    }
}
