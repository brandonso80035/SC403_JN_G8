/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.repository;

import com.topografia.model.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bsoli
 */

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRol(String rol);
}