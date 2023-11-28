
package com.example.proyecto.entidades;
import com.example.proyecto.enumeraciones.EstadoTrabajo;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
@Data
@Entity
public class Trabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(TemporalType.DATE)
    private Date fechaFinalizado;
    private int puntuacionTrabajo;
    private EstadoTrabajo estadoTrabajo;
    
    //private Proveedor proveedor;
    private int horasTrabajo;//Lo completa el provedor x horas de trabajo
    private int precioFinal;//este campo se completa con la horas trabajas x precio hora  trabajada x el provedor
    
    //private Cliente cliente;
}
    
    
            

   
   
    
    //recordar Escribir las Tarjetas de servicios a ServicioServicios.
    

