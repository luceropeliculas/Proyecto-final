
package com.example.proyecto.entidades;
import com.example.proyecto.enumeraciones.Rol;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Proveedor extends Persona{
    
private String matricula;//
private String descripcion;//
private int puntuacionPromedio;//esto es para las estrellitas

@OneToOne
private Rubro rubro;
//private EstadoTrabajo estadoTrabajo;//visualiza en el perfil del proveedor 
private boolean estadoActual;//Depende del estado actual

private double precioHora;
private Date fechaAlta;
private int contdTrabajoRealizado;//esto permite scar un promedio de los trabajos realizados


 // se elimino lista de roles
@Enumerated(EnumType.STRING)
    private Rol rol;
 
 @OneToOne
 private Imagen imagen;
 
}
