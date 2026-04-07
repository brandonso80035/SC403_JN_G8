/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.repository;


import com.topografia.model.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author bsoli
 */



public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    Optional<Usuario> findByUsernameAndActivoTrue(String username);
    
    @Query("SELECT u FROM Usuario u WHERE u.activo = true")
    List<Usuario> findByActivoTrue();
    
    Optional<Usuario> findByUsername(String username);
    
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
    
    Optional<Usuario> findByUsernameOrCorreo(String username, String correo);
    
    boolean existsByUsernameOrCorreo(String username, String correo);
}
