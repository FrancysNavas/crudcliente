package com.cobol.crudcliente.models;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

    @Data
    @Embeddable // Esto indica que esta clase puede ser embebida en otra entidad
    public class NombreApellido {

        @Column(name = "CLI_NOMBRE", length = 20)
        private String nombre;

        @Column(name = "CLI_APELLIDO", length = 25)
        private String apellido;
    }
