/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proyecto.controladores;

import com.example.proyecto.entidades.Comentario;
import com.example.proyecto.repositorios.ComentarioRepositorio;
import com.example.proyecto.servicios.ComentarioServicio;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;





@Controller
@RequestMapping("/comentarios")

public class ComentarioControlador {

    @Autowired
    private ComentarioServicio comentarioServicio;

    @Autowired
    ComentarioRepositorio comentarioRepositorio;// Supongamos que tienes un repositorio de comentarios

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        Optional<Comentario> commentOptional = comentarioRepositorio.findById(id);
        if (commentOptional.isPresent()) {
            Comentario comentario = commentOptional.get();
            if (comentarioServicio.containsForbiddenWords( comentario.getContenido())) {
                comentarioRepositorio.delete(comentario);
                return ResponseEntity.ok("Comentario eliminado debido a contenido inapropiado");
            } else {
                return ResponseEntity.badRequest().body("El comentario no contiene contenido inapropiado");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
