/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 *
 * @author bsoli
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer idUsuario;
    
    @NotBlank
    @Column(name = "USERNAME", unique = true, length = 30, nullable = false)
    private String username;
    
    @Column(name = "PASSWORD", length = 512, nullable = false)    
    private String password;
    
    @NotBlank
    @Column(name = "NOMBRE", length = 20, nullable = false)
    private String nombre;
    
    @NotBlank
    @Column(name = "APELLIDOS", length = 30, nullable = false)
    private String apellidos;
    
    @Email
    @NotBlank
    @Column(name = "CORREO", unique = true, length = 75, nullable = false)
    private String correo;
    
    @Column(name = "TELEFONO", length = 25)
    private String telefono;
    
    @Column(name = "RUTA_IMAGEN", length = 1024)
    private String rutaImagen;
    
    @Column(name = "ACTIVO")
    private boolean activo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "USUARIO_ROL",
        joinColumns = @JoinColumn(name = "ID_USUARIO"),
        inverseJoinColumns = @JoinColumn(name = "ID_ROL")
    )
    private Set<Rol> roles = new HashSet<>();

}
   