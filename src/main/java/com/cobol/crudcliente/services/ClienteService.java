package com.cobol.crudcliente.services;

import com.cobol.crudcliente.models.ClienteModel;
import com.cobol.crudcliente.models.ClienteNoActivoModel;
import com.cobol.crudcliente.models.NroCliente;
import com.cobol.crudcliente.repositories.IClienteNoActivoRepository;
import com.cobol.crudcliente.repositories.IClienteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class ClienteService {

    private final IClienteRepository clienteRepository;
    private final IClienteNoActivoRepository clienteNoActivoRepository;

    public ClienteService(IClienteRepository clienteRepository, IClienteNoActivoRepository clienteNoActivoRepository) {

        this.clienteRepository = clienteRepository;
        this.clienteNoActivoRepository = clienteNoActivoRepository;
    }

    public List<ClienteModel> listarTodos() {

        return clienteRepository.findAll();
    }
    public List<ClienteModel> listarTodosNoActivos() {

        return clienteRepository.findByStatus("0");
    }
    public List<ClienteNoActivoModel> listarNoActivos() {

        return clienteNoActivoRepository.findByStatus("0");
    }

    public Optional<ClienteModel> buscarPorId(NroCliente id) {
        return clienteRepository.findById(id);
    }

    public ClienteModel guardar(ClienteModel cliente) {

        return clienteRepository.save(cliente);
    }

    public void eliminar(NroCliente id) {
        if (!clienteRepository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Cliente no existe: " + id.getNroCuenta() + "-" + id.getDv()
            );
        }
        clienteRepository.deleteById(id);
    }
    @Transactional
    public void eliminacionLogica(NroCliente id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                new EntityNotFoundException("Cliente no existe: " + id.getNroCuenta() + "-" + id.getDv()));
        //validacion si ya es 0
        if ("0".equals(cliente.getStatus())) {
            throw new IllegalStateException(
                    "Cliente ya está dado de baja: " +
                            id.getNroCuenta() + "-" + id.getDv()
            );
        }
        // Copiar a tabla histórica
        ClienteNoActivoModel historico = new ClienteNoActivoModel();
        historico.setNroCliente(cliente.getNroCliente());
        historico.setNroCuit(cliente.getNroCuit());
        historico.setNombreApellido(cliente.getNombreApellido());
        historico.setDomicilio(cliente.getDomicilio());
        historico.setTelefono(cliente.getTelefono());
        historico.setSaldo(cliente.getSaldo());
        historico.setNroSucursal(cliente.getNroSucursal());
        historico.setStatus("0");
        historico.setFechaBaja(LocalDateTime.now());

        clienteNoActivoRepository.save(historico);
        //Baja logica en tabla principal
        cliente.setStatus("0");
        clienteRepository.save(cliente);
    }
    public List<ClienteModel> listarActivos(){
        return clienteRepository.findByStatus("1");
    }
}

