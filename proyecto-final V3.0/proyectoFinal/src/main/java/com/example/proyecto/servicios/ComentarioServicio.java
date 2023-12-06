/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proyecto.servicios;
import com.example.proyecto.entidades.Comentario;
import com.example.proyecto.entidades.Trabajo;
import com.example.proyecto.enumeraciones.EstadoTrabajo;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.repositorios.ComentarioRepositorio;
import com.example.proyecto.repositorios.TrabajoRepositorio;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

/**
 *
 * @author aprig
 */
@Service
public class ComentarioServicio {

    @Autowired
    ComentarioRepositorio comentarioRepositorio;

    @Autowired
    TrabajoRepositorio trabajoRepositorio;

    @Transactional
    public void CrearComentario(String id, String contenido, Integer calificacion, LocalDateTime fechaHora, boolean altaBaja, Long idTrabajo) throws MiException {

        Optional<Trabajo> trabajoRespuesta = trabajoRepositorio.findById(idTrabajo);
        ValidarComentarios(id, contenido, calificacion, altaBaja, fechaHora);

        if (trabajoRespuesta.isPresent()) {
            Trabajo trabajo = trabajoRespuesta.get();

            if (trabajo.getEstadoTrabajo() == EstadoTrabajo.FINALIZADO) {
                LocalDateTime fechaHoraActual = LocalDateTime.now();

                if (trabajoRespuesta.isPresent()) {

                    Comentario comentario = new Comentario(id, contenido, calificacion, fechaHora, altaBaja);
                    comentario.setContenido(contenido);
                    comentario.setCalificacion(calificacion);
                    comentario.setAltaBaja(true);
                    comentario.setFechaHora(fechaHora);
                    comentarioRepositorio.save(comentario);

                } else {
                    throw new MiException("No se pudo obtener la información completa de Cliente o Proveedor.");
                }
            } else {
                throw new MiException("El trabajo debe estar en estado FINALIZADO para agregar un comentario.");
            }
        } else {
            throw new MiException("No se encontró un trabajo con el ID proporcionado: " + idTrabajo);
        }
    }

    @Transactional
    public List<Comentario> ListaComentarios() {

        List<Comentario> comentarios = new ArrayList<>();

        comentarios = comentarioRepositorio.findAll();

        return comentarios;
    }

    @Transactional
    public void ModificarComentarios(String id, String contenido, Integer calificacion, boolean altaBaja, LocalDateTime fechaHora) throws MiException {

        ValidarComentarios(id, contenido, calificacion, altaBaja, fechaHora);
        Optional<Comentario> respuesta = comentarioRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Comentario comentario = respuesta.get();

            comentario.setContenido(contenido);
            comentario.setCalificacion(calificacion);
            comentario.setFechaHora(fechaHora);
            comentarioRepositorio.save(comentario);
        }

    }

    @Transactional
    @Secured("ROLE_ADMIN")
    public void BajaComentario(String id) throws MiException {

        Optional<Comentario> comentarioRespuesta = comentarioRepositorio.findById(id);
       
        if (comentarioRespuesta.isPresent()) {
            Comentario comentario = comentarioRespuesta.get();

            // Verificar si ya está dado de baja
            if (comentario.isAltaBaja()) {
                comentario.setAltaBaja(false);
                comentarioRepositorio.save(comentario);
            } else {
                throw new MiException("El comentario ya está dado de baja.");
            }
        } else {
            throw new MiException("No se encontró un comentario con el ID proporcionado: " + id);
        }
    }
    //   }

    @Transactional
    private void ValidarComentarios(String id, String contenido, Integer calificacion, boolean altaBaja, LocalDateTime fechaHora) throws MiException {

        if (contenido == null || contenido.isEmpty()) {
            throw new MiException(" El comentario no puede estar vacio, por favor complete este campo");

        }
        if (calificacion > 0 && calificacion <= 5) {
            throw new MiException(" La calificacion no puede ser igual a cero,clasificarlo de 1 al 5");

        }
        //  List<Comentario> comentario = new ArrayList<>();
        // comentario = ListaComentarios();

    }

}
