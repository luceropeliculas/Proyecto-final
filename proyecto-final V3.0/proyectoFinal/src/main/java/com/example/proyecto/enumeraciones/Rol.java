
package com.example.proyecto.enumeraciones;




public enum Rol {
    ADMIN("Administrador"),
    PROVEEDOR("Proveedor"),
    CLIENTE("Residente");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    
}


