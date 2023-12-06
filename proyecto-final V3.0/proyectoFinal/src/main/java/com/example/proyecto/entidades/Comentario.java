
package com.example.proyecto.entidades;

import java.time.LocalDateTime;
import java.util.logging.Logger;
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
    private Integer calificacion;
    private LocalDateTime fechaHora;  // Agrega la fecha y hora al comentario
    private boolean altaBaja;

  

    public Comentario(String id, String contenido, Integer calificacion, LocalDateTime fechaHora, boolean altaBaja) {
        this.id = id;
        this.contenido = contenido;
        this.calificacion = calificacion;
        this.fechaHora = fechaHora;
        this.altaBaja = altaBaja;
    }
    
    // Métodos getter para la fecha y hora
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getContenido() {
        return contenido;
    }

    public Integer getCalificacion() {
        return calificacion;
    } 
  /*  
    Con esta modificación, cada vez que se crea un nuevo comentario, se registra la fecha y hora actual. Luego, puedes acceder a esta información a través del método getFechaHora().

Por ejemplo, al imprimir los comentarios en el código principal, podrías mostrar la fecha y hora junto con el contenido y la calificación:
*/
}
