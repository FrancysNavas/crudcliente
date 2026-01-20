package com.cobol.crudcliente.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Domicilio {

    @Column(name = "CLI_CALLE", length = 20)
    private String calle;

    @Column(name = "CLI_NUMERO", length = 6)
    private String numero; // PIC 9(6) → String para conservar ceros

    @Column(name = "CLI_LOCALIDAD", length = 20)
    private String localidad;
}