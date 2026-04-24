/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia;

import com.topografia.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author bsoli
 */


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers("/", "/index", "/login", "/registro/**", "/acceso_denegado",
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/servicios", "/proyectos", "/noticias", "/blog",
                                "/contacto", "/sobre-nosotros").permitAll()
                .requestMatchers("/perfil", "/cambiar-password").authenticated()
                .requestMatchers("/admin/**", "/usuario/**").hasRole("ADMIN")
                .requestMatchers("/contenido/editar/**", "/noticias/editar/**", "/blog/editar/**")
                    .hasAnyRole("ADMIN", "EDITOR")
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
        )
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
        )
        .exceptionHandling(exceptions -> exceptions
                .accessDeniedPage("/acceso_denegado")
        );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UsuarioDetailsService userDetailsService) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
