/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proyecto.controladores;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;



import com.example.proyecto.entidades.Rubro;

import com.example.proyecto.servicios.ClienteServicio;
import com.example.proyecto.servicios.RubroServicio;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author victo
 */
@Controller /* para que spring sepa que es un controlador */
@RequestMapping("/") /* cuando cargue va al principal */
public class PortalControlador {
    @Autowired
    RubroServicio rubroServicio;
 
    @Autowired
    ClienteServicio clienteServicio;
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_CLIENTE','ROLE_ADMIN','ROLE_PROVEEDOR')")
    @GetMapping("/inicio")
    public String index1() {
        return "index1.html";
    }

    @GetMapping("/login1")
    public String loginInicio() {
        return "login1.html";
    }

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        List<Rubro> rubros = rubroServicio.ListaRubros();
        modelo.addAttribute("rubros", rubros);

        return "registroDoble.html";
    }


    




}
