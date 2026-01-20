package com.cobol.crudcliente.repositories;

import com.cobol.crudcliente.models.ClienteModel;
import com.cobol.crudcliente.models.NroCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<ClienteModel, NroCliente> {
    // Al extender JpaRepository ya tenemos save, findAll, findById, deleteById, etc.
    List<ClienteModel> findByStatus(String status);

}
