package com.cobol.crudcliente.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "clientes_noactivos")
public class ClienteNoActivoModel {

    @EmbeddedId
    private NroCliente nroCliente;   // PIC 9(10)

    @Column(name = "CLI_NRO_CUIT", length = 11)
    @Size(max = 11)
    @NotNull
    private String nroCuit; // PIC 9(11)

    @Embedded
    private NombreApellido nombreApellido; // 05 CLI-NOMAPEL

    @Embedded
    private Domicilio domicilio; // 05 CLI-DOMICILIO

    @Column(name = "CLI_TELEFONO", length = 10)
    @Size(max = 10)
    @Digits(integer = 10, fraction = 0)
    private String telefono; // PIC 9(10)

    @Column(name = "CLI_SALDO", precision = 10)
    @Digits(integer = 10, fraction = 0)
    private Long saldo; // PIC 9(10)

    @Column(name = "CLI_NRO_SUCURSAL", length = 3)
    @Size(max = 3)
    private String nroSucursal; // PIC 9(3)

    @Column(name = "CLI_STATUS", length = 1)
    @Size(max = 1)
    private String status; // PIC 9(1)

    @Column(name = "FECHA_BAJA")
    private LocalDateTime fechaBaja;
}

