package com.cobol.crudcliente.controller;

import com.cobol.crudcliente.models.ClienteModel;
import com.cobol.crudcliente.models.ClienteNoActivoModel;
import com.cobol.crudcliente.models.NroCliente;
import com.cobol.crudcliente.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteModel> obtenerTodos() {
        return clienteService.listarTodos();
    }

    @GetMapping("/{nroCuenta}/{dv}")
    public ResponseEntity<ClienteModel> obtenerPorId(@PathVariable String nroCuenta, @PathVariable String dv) {
        NroCliente id = new NroCliente();
        id.setNroCuenta(nroCuenta);
        id.setDv(dv);

        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activos")
    public List<ClienteModel> listarActivos(){
        return clienteService.listarActivos();
    }
    @GetMapping("/statuscero")
    public List<ClienteModel> listarTodosNoActivos(){
        return clienteService.listarTodosNoActivos();
    }
    @GetMapping("/noactivos")
    public List<ClienteNoActivoModel> listarNoActivos(){
        return clienteService.listarNoActivos();
    }

    @PostMapping
    public ClienteModel crear(@Valid @RequestBody ClienteModel cliente) {
        return clienteService.guardar(cliente);
    }

    @PutMapping("/{nroCuenta}/{dv}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable String nroCuenta, @PathVariable String dv, @Valid @RequestBody ClienteModel cliente) {
        NroCliente id = new NroCliente(nroCuenta, dv);

        cliente.setNroCliente(id);
        return ResponseEntity.ok(clienteService.guardar(cliente));
    }
    @DeleteMapping("/{nroCuenta}/{dv}")
    public ResponseEntity<Void> eliminacionLogica(@PathVariable String nroCuenta, @PathVariable String dv) {

        clienteService.eliminacionLogica(new NroCliente(nroCuenta, dv));
        return ResponseEntity.noContent().build();
    }
    // delete fisico
    //@DeleteMapping("/{nroCuenta}/{dv}")
    //public ResponseEntity<Void> eliminar(@PathVariable String nroCuenta, @PathVariable String dv) {
    //    NroCliente id = new NroCliente();
    //    id.setNroCuenta(nroCuenta);
    //    id.setDv(dv);
    //    clienteService.eliminar(id);
    //    return ResponseEntity.noContent().build();
    //}
    // delete logica

}