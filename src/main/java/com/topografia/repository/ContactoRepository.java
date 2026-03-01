/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.repository;

import com.topografia.model.Contacto;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;


/**
 *
 * @author bsoli
 */
@Repository
public class ContactoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registrarContacto(String nombre, String telefono,
                                   String email, String servicio, String mensaje) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_CONTACTO")
                .withProcedureName("SP_REGISTRAR_CONTACTO");

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("P_NOMBRE",   nombre)
                .addValue("P_TELEFONO", telefono)
                .addValue("P_EMAIL",    email)
                .addValue("P_SERVICIO", servicio)
                .addValue("P_MENSAJE",  mensaje);

        call.execute(params);
    }

    public List<Contacto> listarContactos() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_CONTACTO")
                .withFunctionName("FN_LISTAR_CONTACTOS")
                .returningResultSet("RESULTADO", (rs, rowNum) -> {
                    Contacto c = new Contacto();
                    c.setIdContacto(rs.getLong("ID_CONTACTO"));
                    c.setNombre(rs.getString("NOMBRE"));
                    c.setTelefono(rs.getString("TELEFONO"));
                    c.setEmail(rs.getString("EMAIL"));
                    c.setServicio(rs.getString("SERVICIO"));
                    c.setMensaje(rs.getString("MENSAJE"));
                    c.setEstado(rs.getString("ESTADO"));
                    if (rs.getDate("FECHA_ENVIO") != null) {
                        c.setFechaEnvio(rs.getDate("FECHA_ENVIO").toLocalDate());
                    }
                    return c;
                });

        Map<String, Object> result = call.execute();
        return (List<Contacto>) result.get("RESULTADO");
    }
}
