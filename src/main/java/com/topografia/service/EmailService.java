/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author bsoli
 */



@Service
public class EmailService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    

    public void enviarCorreoRecuperacion(String to, String resetUrl) {
        // Solo muestra el enlace en consola para expo
        logger.info("Use el siguente enlace");
        logger.info("Para: {}", to);
        logger.info("Enlace: {}", resetUrl);
        
    }
}
