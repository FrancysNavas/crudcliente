package com.cobol.crudcliente.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class NroCliente implements Serializable {

    @Column(name = "CLI_NRO_CUENTA", length = 9, nullable = false)
    @Size(max = 9)
    private String nroCuenta;   // PIC 9(9)

    @Column(name = "CLI_DV", length = 1, nullable = false)
    @Size(max = 1)
    private String dv;          // PIC 9(1)

    public NroCliente() { }

    public NroCliente(String nroCuenta, String dv) {
        this.nroCuenta = nroCuenta;
        this.dv = dv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NroCliente)) return false;
        NroCliente that = (NroCliente) o;
        return Objects.equals(nroCuenta, that.nroCuenta) &&
                Objects.equals(dv, that.dv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroCuenta, dv);
    }
}