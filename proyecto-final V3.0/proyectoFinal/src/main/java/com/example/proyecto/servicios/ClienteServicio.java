package com.example.proyecto.servicios;

import com.example.proyecto.entidades.Cliente;
import com.example.proyecto.entidades.Imagen;
import com.example.proyecto.enumeraciones.Rol;
import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.repositorios.ClienteRepositorio;
import com.example.proyecto.repositorios.ImagenRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ClienteServicio {

    @Autowired
    ClienteRepositorio clienteRepositorio;
    @Autowired
    ImagenServicio imagenServicio;

    @Transactional
    public void crearCliente(MultipartFile archivo, String nombre, String apellido, String dni, String telefono,
            String email, String password,String password2, String domicilio) throws MiException {
//se agrego string domicilio
// no recibia pasword 2

        validar(nombre, apellido, dni, telefono, email, password, password2);
        Cliente cliente = new Cliente();
        cliente.setDomicilio(domicilio);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setPassword(password);
        cliente.setAlta(true);
        cliente.setFechaAlta(new Date());

        // Crear un conjunto de roles para el cliente
       
     cliente.setRol(Rol.CLIENTE);
     //se elimino lista de roles
     
     
        // llamamos al metodo guardar q se encargara de guardar la foto en la base de
        // dato.
        Imagen imagen = imagenServicio.guardar(archivo);
        cliente.setImagen(imagen);

        clienteRepositorio.save(cliente);

    }
//hay que ver este metodo (iria en proveedor)
    @Transactional
    public void cambiarRol(String id) {
        Optional<Cliente> respuesta = clienteRepositorio.findById(id);

        if (respuesta.isPresent()) {

            Cliente cliente = respuesta.get();
/*
            if (cliente.getRoles().equals(Rol.CLIENTE)) {
                Set<Rol> roles = new HashSet<>();
                roles.add(Rol.PROVEEDOR);
            //    cliente.setRoles(roles);

            } else if (cliente.getRoles().equals(Rol.PROVEEDOR)) {
                Set<Rol> roles = new HashSet<>();
                roles.add(Rol.CLIENTE);
             //   cliente.setRoles(roles);
            }
*/
        }
    }

    @Transactional
    private void validar(String nombre, String apellido, String dni, String telefono, String email, String password,
            String password2) throws MiException {
         // no se valida domicilio
        
        
        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (apellido.isEmpty() || apellido == null) {
            throw new MiException("El nombre no puede ser nulo o estar vacio");
        }

        if (dni.isEmpty() || dni == null) {
            throw new MiException("El dni no puede ser nulo o estar vacio");
        }

        if (telefono.isEmpty() || telefono == null) {
            throw new MiException("El telefono no puede ser nulo o estar vacio");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");

            // para mas adelante hacer un validador de contarseñas ejemplo que no se a000000 123456 etc
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

    public Cliente getOne(String id) {
        return clienteRepositorio.getOne(id);
    }

    //metodo redundante ya tenemos abajo el metodo actualizar
    
    // Actualizar el cliente en la base de datos
    public void actulizarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);
    }
// no usar notaciones que nuestros compañeros no sepan 
    // no iria el transactional por que no interactuamos con la base de datos
    @Transactional(readOnly = true)
    public List<Cliente> listarCliente() {

        List<Cliente> clientes = new ArrayList<>();

        clientes = clienteRepositorio.findAll();

        return clientes;
    }

    // Modificar Cliente
    @Transactional
    public void actualizar(MultipartFile archivo, String nombre, String apellido, String dni, String telefono,
            String email, String password, String password2) throws MiException {
// no recibe direccion

        validar(nombre, apellido, dni, email, telefono, password, password2);
        // `Optional<Cliente>` significa que la respuesta puede contener un objeto
        // `Cliente` o no (puede ser nulo).
        Optional<Cliente> respuesta = clienteRepositorio.findById(dni);// findById(dni)**: Este método busca un objeto
                                                                       // en la base de datos con el `dni`
                                                                       // proporcionado.
        if (respuesta.isPresent()) {
/// setear domicilio
            Cliente cliente = respuesta.get();
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setDni(dni);
            cliente.setTelefono(telefono);
            cliente.setEmail(nombre);
            cliente.setPassword(nombre);

            
            ///arreglar roles
            // Crear un conjunto de roles para el cliente
            Set<Rol> roles = new HashSet<>();
            roles.add(Rol.CLIENTE);
            cliente.setPassword(new BCryptPasswordEncoder().encode(password));

          //  cliente.setRoles(roles);

            String idImagen = null;

            if (cliente.getImagen() != null) {
                idImagen = cliente.getImagen().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            cliente.setImagen(imagen);

            clienteRepositorio.save(cliente);
        }

    }
/// falta el metodo para no eliminar(recordar usar un boolenao alta//baja)
    @Transactional
    public void delete(String dni) {
        try {
            clienteRepositorio.deleteById(dni);
        } catch (Exception e) {
        }
    }

}
