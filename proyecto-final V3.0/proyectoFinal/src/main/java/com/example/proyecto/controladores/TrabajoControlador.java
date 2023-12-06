
package com.example.proyecto.controladores;
import com.example.proyecto.entidades.Persona;
import com.example.proyecto.entidades.Proveedor;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.servicios.ProveedorServicio;
import com.example.proyecto.servicios.TrabajoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
 @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
@RequestMapping("/trabajo")
public class TrabajoControlador {
 
 @Autowired
 TrabajoServicio trabajoServicio;
 @Autowired
 ProveedorServicio proveedorServicio;
    
        @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
@GetMapping("/contratar/{dni}")
public String contratar (@PathVariable String dni, ModelMap modelo, HttpSession session) {

    Proveedor proveedor = proveedorServicio.getOne(dni);
    /*PARA EXPLICAR QUE HACE*/   
          
       System.out.println("--------------------------------");
    
    
      Persona cliente=(Persona)session.getAttribute("usuariosession");

      
modelo.put("cliente", cliente);           
        
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
