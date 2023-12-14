package com.example.proyecto.controladores;

import com.example.proyecto.entidades.Comentario;
import com.example.proyecto.entidades.Persona;
import com.example.proyecto.entidades.Proveedor;
import com.example.proyecto.entidades.Rubro;
import com.example.proyecto.entidades.Trabajo;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.servicios.ComentarioServicio;
import com.example.proyecto.servicios.ProveedorServicio;
import com.example.proyecto.servicios.RubroServicio;
import com.example.proyecto.servicios.TrabajoServicio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author AMD
 */
@Controller
@RequestMapping("/proveedor")
public class ProveedorControlador {

    @Autowired
    ProveedorServicio proveedorServicio;
    @Autowired
    RubroServicio rubroServicio;
    @Autowired
    TrabajoServicio trabajoServicio;
    @Autowired
    ComentarioServicio comentarioServicio;

    //  falta el campo descripcion!!!!!!!!!!!!!
    @PostMapping("/registro")
    public String registro(String validador, String nombre, String apellido, String dni, String telefono,
            String direccion, String email, String password, String matricula,
            Double precioHora, String descripcion,
            ModelMap modelo, MultipartFile archivo, String password2, String idRubro) {
        try {

            proveedorServicio.validar3(precioHora, idRubro, matricula,descripcion);

            modelo.put("exito", "COMPLETA TUS DATOS PERSONALES PARA CONTINUAR");
            modelo.put("validador", validador);
            modelo.put("precioHora", precioHora);
            modelo.put("matricula", matricula);
            modelo.put("idRubro", idRubro);

            return "registroDoble.html";

        } catch (MiException ex) {

            List<Rubro> rubros = rubroServicio.ListaRubros();
            modelo.addAttribute("rubros", rubros);

            modelo.put("validador", validador);
            modelo.put("precioHora", precioHora);
            modelo.put("matricula", matricula);
            modelo.put("idRubro", idRubro);

            modelo.put("error", ex.getMessage());
            return "registroDoble.html";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/listar/{rubro}")
    public String listar(@PathVariable String rubro, ModelMap modelo) {

        List<Proveedor> proveedores = new ArrayList<>();
        proveedores = proveedorServicio.listarPorRubro(rubro);

        modelo.addAttribute("proveedores", proveedores);

        return "proveedor_list2.html";
    }

    @GetMapping("/")
    public String verPagInicio(Model modelo, @Param("palabraClave") String palabraClave) {
        List<Proveedor> listProveedors = proveedorServicio.listAll(palabraClave);
        modelo.addAttribute("listProveedors", listProveedors);
        modelo.addAttribute("palabraClave", palabraClave);

        return "index.html";

    }

    @GetMapping("/listarTrabajo")
    public String listarTrabajo(ModelMap modelo, HttpSession session) {

        Persona proveedor = (Persona) session.getAttribute("usuariosession");
        List<Trabajo> trabajos = trabajoServicio.listarTrabajoProveedor(proveedor.getDni());
        modelo.addAttribute("trabajos", trabajos);

        return "listar_trabajo_proovedor.html";
    }

    @GetMapping("/perfil/{dni}")
    public String listarTrabajo(@PathVariable String dni, ModelMap modelo) {

        Proveedor proveedor = proveedorServicio.getOne(dni);

        List<Comentario> comentarios = comentarioServicio.ListaComentariosPorProveedor(dni);

        modelo.addAttribute("comentarios", comentarios);

        modelo.addAttribute("proveedor", proveedor);

        return "perfilProveedor.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        Proveedor proveedor = (Proveedor) session.getAttribute("personasession");
        modelo.put("proveedor", proveedor);
        return "proveedor_modificar.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROVEEDOR', 'ROLE_ADMIN')")
    @GetMapping("/modificarProveedor")
    public String perfil2(ModelMap modelo, HttpSession session) {
        Persona persona = (Proveedor) session.getAttribute("usuariosession");
        if (persona instanceof Proveedor) {
            Proveedor proveedor = (Proveedor) persona;
            modelo.put("proveedor", proveedor);
            return "modificarProvedor.html";
        } else {
            System.out.println("error aquiiiii");
            // Manejar el caso en que la instancia no sea de tipo Cliente
            // Puedes redirigir a una página de error o manejar de otra manera
            return "index1";
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @PostMapping("/modificado")
    public String modificaPersonales(String dni, String nombre,
            MultipartFile archivo, String apellido, String telefono,
            String matricula, Double precioHora, String descripcion, String idRubro,
            String email, String domicilio, ModelMap modelo, String validador, HttpSession session) {

       
        try {
             

            proveedorServicio.modificar(archivo, nombre, apellido, dni, telefono, email, domicilio);
           
            Proveedor proveedorActualizado = proveedorServicio.getOne(dni);
            session.setAttribute("usuariosession", proveedorActualizado);
            modelo.put("exito", "Proveedor actualizado correctamente!");
          

        } catch (MiException ex) {
 List<Rubro> rubros = rubroServicio.ListaRubros();
            modelo.addAttribute("rubros", rubros);
            modelo.put("verificador", 2);
           Proveedor proveedor = proveedorServicio.getOne(dni);
            modelo.put("cliente", proveedor);
              modelo.put("error", ex.getMessage());
            return "modificarDatos.html";
        }
         List<Rubro> rubros = rubroServicio.ListaRubros();
            modelo.addAttribute("rubros", rubros);
  return "index1.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @PostMapping("/modificado1")
    public String modificado1(String dni, String nombre,
            MultipartFile archivo, String apellido, String telefono,
            String matricula, Double precioHora, String descripcion, String idRubro,
            String email, String domicilio, ModelMap modelo, String validador) {

        try {
            proveedorServicio.modificarOficio(dni, matricula, descripcion, precioHora, idRubro);
            
            modelo.put("exito", "Oficio actualizado correctamente!");
        } catch (MiException ex) {
            
            
 List<Rubro> rubros = rubroServicio.ListaRubros(); 
            modelo.addAttribute("rubros", rubros);
            modelo.put("verificador", 2);
                     
            
            modelo.put("precioHora", precioHora);
            modelo.put("matricula", matricula);
            modelo.put("idRubro", idRubro);
            modelo.put("validador", validador);
            modelo.put("descripcion", descripcion);
            modelo.put("error", ex.getMessage());
            return "modificarDatos.html";
        }
         List<Rubro> rubros = rubroServicio.ListaRubros();
            modelo.addAttribute("rubros", rubros);
        return "index1.html";
    }
}
