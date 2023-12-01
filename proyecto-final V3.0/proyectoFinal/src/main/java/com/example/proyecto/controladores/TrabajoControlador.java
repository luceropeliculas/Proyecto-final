
package com.example.proyecto.controladores;

import com.example.proyecto.entidades.Proveedor;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.servicios.ProveedorServicio;
import com.example.proyecto.servicios.TrabajoServicio;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/trabajo")
public class TrabajoControlador {
 
 @Autowired
 TrabajoServicio trabajoServicio;
 @Autowired
 ProveedorServicio proveedorServicio;
    
    
@GetMapping("/contratar/{dni}")
public String contratar (@PathVariable String dni, ModelMap modelo) {
    
    Proveedor proveedor = new Proveedor();
    
    proveedor = proveedorServicio.getOne(dni);
    
    modelo.addAttribute("proveedor",proveedor);
   
    return "registroTrabajo.html";
}

@PostMapping("/registro")
public String registro (String dniProveedor, String dniCliente, String detalleDeSolicitud ) {
    
    
     try {
         trabajoServicio.solicitudTrabajo(dniProveedor, dniCliente, detalleDeSolicitud);
     } catch (MiException ex) {
         return "registroTrabajo.html";
     }
   
    return "index.html";
}







   
    
    
    
}
