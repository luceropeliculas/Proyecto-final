/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proyecto.controladores;

import com.example.proyecto.excepciones.MiException;
import com.example.proyecto.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author victo
 */
@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
    
    @Autowired
    ClienteServicio clienteServicio;
    
    @GetMapping("/registrar")
    public String registrar(){
    
return "cliente_form.html";
        }
    
      @PostMapping("/registro")
    public String registro(@RequestParam String nombre,String apellido,String dni,String telefono, 
 String direccion, String email,String password, ModelMap modelo,MultipartFile archivo, String password2){
        //se agrego string direccion
        try {
            clienteServicio.crearCliente(archivo, nombre, apellido, dni, telefono, email, password, password2, direccion);
            
            modelo.put("exito", "El Cliente fue registrado correctamente!");
        } catch (MiException ex) {
                      
            modelo.put("error", ex.getMessage());
            return "cliente_form.html";
        }
        
        return "index.html";        
    }
}
