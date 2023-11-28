/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.proyecto.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author victo
 */
@Controller                    /*para que spring sepa que es un controlador*/
@RequestMapping("/") /*cuando cargue va al principal*/
public class PortalControlador {
    @GetMapping("/")        
    public String index(){
    return"index.html";
    }
  
}
