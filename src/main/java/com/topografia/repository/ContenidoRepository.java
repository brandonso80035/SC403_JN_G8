/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.topografia.repository;

import com.topografia.model.Blog;
import com.topografia.model.Noticia;
import com.topografia.model.Proyecto;
import com.topografia.model.Servicio;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author bsoli
 */
@Repository
public class ContenidoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
public List<Servicio> listarServicios() {

    SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
            .withCatalogName("PKG_CONTENIDO")
            .withFunctionName("FN_LISTAR_SERVICIOS")
            .returningResultSet("RESULTADO", (rs, rowNum) -> {
                Servicio s = new Servicio();
                s.setIdServicio(rs.getLong("ID_SERVICIO"));
                s.setNombre(rs.getString("NOMBRE"));
                s.setDescripcion(rs.getString("DESCRIPCION"));
                s.setPrecioDesde(rs.getDouble("PRECIO_DESDE"));
                s.setImagenUrl(rs.getString("IMAGEN_URL"));
                return s;
            });

    return call.executeFunction(List.class);
}
    public List<Proyecto> listarProyectos() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_CONTENIDO")
                .withFunctionName("FN_LISTAR_PROYECTOS")
                .returningResultSet("RESULTADO", (rs, rowNum) -> {
                    Proyecto p = new Proyecto();
                    p.setIdProyecto(rs.getLong("ID_PROYECTO"));
                    p.setTitulo(rs.getString("TITULO"));
                    p.setDescripcion(rs.getString("DESCRIPCION"));
                    p.setUbicacion(rs.getString("UBICACION"));
                    p.setImagenUrl(rs.getString("IMAGEN_URL"));
                    if (rs.getDate("FECHA") != null) {
                        p.setFecha(rs.getDate("FECHA").toLocalDate());
                    }
                    return p;
                });

        Map<String, Object> result = call.execute();
        return (List<Proyecto>) result.get("RESULTADO");
    }

    public List<Noticia> listarNoticias() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_CONTENIDO")
                .withFunctionName("FN_LISTAR_NOTICIAS")
                .returningResultSet("RESULTADO", (rs, rowNum) -> {
                    Noticia n = new Noticia();
                    n.setIdNoticia(rs.getLong("ID_NOTICIA"));
                    n.setTitulo(rs.getString("TITULO"));
                    n.setContenido(rs.getString("CONTENIDO"));
                    if (rs.getDate("FECHA") != null) {
                        n.setFecha(rs.getDate("FECHA").toLocalDate());
                    n.setImagenUrl(rs.getString("IMAGEN_URL"));
                    }
                    return n;
                });

        Map<String, Object> result = call.execute();
        return (List<Noticia>) result.get("RESULTADO");
    }

    public List<Blog> listarBlog() {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withCatalogName("PKG_CONTENIDO")
                .withFunctionName("FN_LISTAR_BLOG")
                .returningResultSet("RESULTADO", (rs, rowNum) -> {
                    Blog b = new Blog();
                    b.setIdBlog(rs.getLong("ID_BLOG"));
                    b.setTitulo(rs.getString("TITULO"));
                    b.setContenido(rs.getString("CONTENIDO"));
                    b.setCategoria(rs.getString("CATEGORIA"));
                    if (rs.getDate("FECHA") != null) {
                        b.setFecha(rs.getDate("FECHA").toLocalDate());
                    }
                    return b;
                });

        Map<String, Object> result = call.execute();
        return (List<Blog>) result.get("RESULTADO");
    }
}
