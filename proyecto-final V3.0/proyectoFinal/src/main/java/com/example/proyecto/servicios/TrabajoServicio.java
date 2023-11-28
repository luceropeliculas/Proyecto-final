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
public Trabajo contrataTrabajo(String dniProveedor, String dniCliente) throws MiException{


   
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

    return trabajo;

}
@Transactional
public void aceptacionDeTrabajoProveedor (Long idtrabajo ){

   

   boolean EstadoDeAceptacion = false;

   if (EstadoDeAceptacion) {
      trabajo.setEstadoTrabajo(EstadoTrabajo.ACEPTADO);
      trabajo.setFechaInicio(new Date());
      trabajo.setHorasTrabajo(0);


      trabajo.setPrecioFinal(0);
      

      


   }else{

   }

}
    
}
