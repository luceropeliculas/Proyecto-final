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

@Transactional
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
@Transactional
public void revisionDeTrabajo(Long idtrabajo ,
 Boolean aceptacion,String respuestaProveedor, int gastosAdicionales, int horasTrabajadasEstimadas, String dniProveedor){

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
  trabajo.setRespuestaProveedor(respuestaProveedor);
   }

}
    
}
