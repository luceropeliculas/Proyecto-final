package com.example.proyecto.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.proyecto.entidades.Trabajo;

@Repository
public interface TrabajoRepositorio extends JpaRepository<Trabajo, Long>{

   
  
}


