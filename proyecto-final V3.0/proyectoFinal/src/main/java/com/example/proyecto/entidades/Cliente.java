
 package com.example.proyecto.entidades;



import com.example.proyecto.enumeraciones.Rol;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.Data;


@Data
@Entity
public class Cliente extends Persona{

  @Enumerated(EnumType.STRING)
    private Rol rol;
  
    
}
