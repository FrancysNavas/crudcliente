package com.cobol.crudcliente.repositories;

import com.cobol.crudcliente.models.ClienteNoActivoModel;
import com.cobol.crudcliente.models.NroCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClienteNoActivoRepository extends JpaRepository<ClienteNoActivoModel, NroCliente> {
    List<ClienteNoActivoModel> findByStatus(String status);
}
