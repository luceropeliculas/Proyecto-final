
package com.example.proyecto.controladores;

import com.example.proyecto.entidades.Cliente;
import com.example.proyecto.entidades.Comentario;
import com.example.proyecto.entidades.Persona;
import com.example.proyecto.entidades.Proveedor;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.servicios.ComentarioServicio;
import com.example.proyecto.servicios.ProveedorServicio;
import com.example.proyecto.servicios.TrabajoServicio;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
 @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
@RequestMapping("/trabajo")
public class TrabajoControlador {
 
 @Autowired
 TrabajoServicio trabajoServicio;
 @Autowired
 ProveedorServicio proveedorServicio;
 @Autowired
 ComentarioServicio comentarioServicio;
    
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

//id trabajo, comentario, calificacion 
    @GetMapping("/comentar/{idTrabajo}")
    public String mostrarFormularioComentario(@PathVariable Long idTrabajo, ModelMap modelo) {
        
        modelo.addAttribute("idTrabajo", idTrabajo);

        return "comentarTrabajo.html";
    }

    @PostMapping("/comentar")
    public String comentar(
            @RequestParam String idComentario,
            @RequestParam Long idTrabajo,
            @RequestParam String contenido,
            @RequestParam Integer calificacion,
            @RequestParam Boolean altaBaja){
        
        try {
            comentarioServicio.CrearComentario(idComentario, contenido, 0, LocalDateTime.MAX, true, idTrabajo);
             } catch (MiException ex) {
                
      
            return "comentarTrabajo.html";
        }

        return "index.html";
    }
}
