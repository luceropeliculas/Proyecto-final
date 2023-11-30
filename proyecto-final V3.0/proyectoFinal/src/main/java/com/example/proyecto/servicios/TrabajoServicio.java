package com.example.proyecto.servicios;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto.entidades.Cliente;
import com.example.proyecto.entidades.Proveedor;
//import com.example.proyecto.entidades.Rubro;
import com.example.proyecto.entidades.Trabajo;
import com.example.proyecto.enumeraciones.EstadoTrabajo;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.repositorios.ClienteRepositorio;
import com.example.proyecto.repositorios.ProveedorRepositorio;
import com.example.proyecto.repositorios.TrabajoRepositorio;

@Service
public class TrabajoServicio {
   Trabajo trabajo = new Trabajo(); 
   Cliente cliente = new Cliente();
   Proveedor proveedor = new Proveedor();

@Autowired
ClienteRepositorio clienteRepositorio;
@Autowired
ProveedorRepositorio proveedorRepositorio;
@Autowired
TrabajoRepositorio trabajoRepositorio;

@Transactional //lo crea el cliente
public Trabajo solicitudTrabajo(String dniProveedor, String dniCliente, String detalleDeTrabajo) throws MiException{

   Optional<Cliente> clienteRespuesta = clienteRepositorio.findById(dniCliente);

         if (clienteRespuesta.isPresent()) {
            cliente = clienteRespuesta.get();
         }
 Optional<Proveedor> proveedorRespuesta = proveedorRepositorio.findById(dniProveedor);

         if (proveedorRespuesta.isPresent()) {
            proveedor = proveedorRespuesta.get();
         }

   trabajo.setProveedor(proveedor);
   trabajo.setCliente(cliente);
   trabajo.setEstadoTrabajo(EstadoTrabajo.PENDIENTE);
    trabajo.setDetalleDeSolicitud(detalleDeTrabajo);  
    return trabajo;

}
@Transactional // le llega al proveedor lo acepta o no, y genera el presupúesto
public void revisionDeTrabajo(Long idtrabajo ,
 Boolean aceptacion,String respuestaProveedor, int gastosAdicionales, int horasTrabajadasEstimadas, String dniProveedor, String observacionCancelado){

Trabajo trabajo = new Trabajo();

 Optional<Trabajo> trabajoRespuesta = trabajoRepositorio.findById(idtrabajo);

 
         if (trabajoRespuesta.isPresent()) {
            trabajo = trabajoRespuesta.get();
         }

   if (aceptacion) {
      trabajo.setEstadoTrabajo(EstadoTrabajo.REVISION);
      trabajo.setFechaInicio(new Date());
    
      trabajo.setRespuestaProveedor(respuestaProveedor);
      trabajo.setHorasTrabajoEstimadas(horasTrabajadasEstimadas);
      trabajo.setGastosAdicionales(gastosAdicionales);

 Optional<Proveedor> proveedorRespuesta = proveedorRepositorio.findById(dniProveedor);

         if (proveedorRespuesta.isPresent()) {
            proveedor = proveedorRespuesta.get();
         }

double preciofinal;

preciofinal = (proveedor.getPrecioHora()*trabajo.getHorasTrabajoEstimadas()) + trabajo.getGastosAdicionales();
      
trabajo.setPrecioFinal(preciofinal);


   }else{
  trabajo.setEstadoTrabajo(EstadoTrabajo.CANCELADO);
  trabajo.setObservacionCancelado(observacionCancelado);
   }

}

@Transactional
    public void aceptacionCliente(Long idTrabajo, boolean aceptacion, String observacionCancelado) throws MiException {
        Optional<Trabajo> trabajoRespuesta = trabajoRepositorio.findById(idTrabajo);

        if (trabajoRespuesta.isPresent()) {
            Trabajo trabajo = trabajoRespuesta.get();

            if (aceptacion) {
                trabajo.setEstadoTrabajo(EstadoTrabajo.ACEPTADO);



            } else {
                trabajo.setEstadoTrabajo(EstadoTrabajo.CANCELADO);
                trabajo.setObservacionCancelado(observacionCancelado);
            }

            trabajoRepositorio.save(trabajo);
        } else {
            throw new MiException("No se encontró un trabajo con el ID proporcionado: " + idTrabajo);
        }
    }
    @Transactional
    public void finalizarTrabajo(Long idTrabajo) throws MiException {
        Optional<Trabajo> trabajoRespuesta = trabajoRepositorio.findById(idTrabajo);

        if (trabajoRespuesta.isPresent()) {
            Trabajo trabajo = trabajoRespuesta.get();

            if (trabajo.getEstadoTrabajo() == EstadoTrabajo.FINALIZADO ) {
                throw new MiException("El trabajo ya está marcado como finalizado.");
            }

            trabajo.setEstadoTrabajo(EstadoTrabajo.FINALIZADO);
            trabajoRepositorio.save(trabajo);
        } else {
            throw new MiException("No se encontró un trabajo con el ID proporcionado: " + idTrabajo);
        }
    }

    @Transactional
    public void ComentarioYpuntuacion(Long idTrabajo, String comentario, int puntuacion) throws MiException {
        Optional<Trabajo> trabajoRespuesta = trabajoRepositorio.findById(idTrabajo);

        if (trabajoRespuesta.isPresent()) {
            Trabajo trabajo = trabajoRespuesta.get();

            if (trabajo.getEstadoTrabajo() != EstadoTrabajo.FINALIZADO) {
                throw new MiException("El trabajo debe estar marcado como finalizado para agregar un comentario y puntuación.");
            }

            trabajo.setCometarioTrabajoTerminado(comentario);
            trabajo.setPuntuacionTrabajo(puntuacion);

            trabajoRepositorio.save(trabajo);
        } else {
            throw new MiException("No se encontró un trabajo con el ID proporcionado: " + idTrabajo);
        }
    }


    
}
