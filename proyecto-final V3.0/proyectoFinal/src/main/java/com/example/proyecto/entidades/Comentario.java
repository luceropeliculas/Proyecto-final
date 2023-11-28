
package com.example.proyecto.entidades;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
@Data
@Entity
public class Comentario {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String contenido;
    private int calificacion;
    private LocalDateTime fechaHora;  // Agrega la fecha y hora al comentario

    public Comentario(String contenido, int calificacion) {
        this.contenido = contenido;
        this.calificacion = calificacion;
        this.fechaHora = LocalDateTime.now();  // Establece la fecha y hora actual al crear el comentario
    }

    // Métodos getter para la fecha y hora
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getContenido() {
        return contenido;
    }

    public int getCalificacion() {
        return calificacion;
    } 
  /*  
    Con esta modificación, cada vez que se crea un nuevo comentario, se registra la fecha y hora actual. Luego, puedes acceder a esta información a través del método getFechaHora().

Por ejemplo, al imprimir los comentarios en el código principal, podrías mostrar la fecha y hora junto con el contenido y la calificación:
*/
}
