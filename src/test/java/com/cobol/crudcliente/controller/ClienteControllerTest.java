package com.cobol.crudcliente.controller;

import com.cobol.crudcliente.models.ClienteModel;
import com.cobol.crudcliente.models.ClienteNoActivoModel;
import com.cobol.crudcliente.models.NroCliente;
import com.cobol.crudcliente.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ClienteService clienteService;

    private ClienteModel clienteModel;
    private ClienteNoActivoModel clienteNoActivoModel;
    private NroCliente nroCliente;

    @BeforeEach
    void setUp() {
        nroCliente = new NroCliente();
        nroCliente.setNroCuenta("1234567890");
        nroCliente.setDv("1");

        clienteModel = new ClienteModel();
        clienteModel.setNroCliente(nroCliente);
        clienteModel.setNroCuit("20123456789");
        clienteModel.setStatus("1");

        clienteNoActivoModel = new ClienteNoActivoModel();
        clienteNoActivoModel.setNroCliente(nroCliente);
        clienteNoActivoModel.setNroCuit("20123456789");
        clienteNoActivoModel.setStatus("0");
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeClientes() throws Exception {
        List<ClienteModel> clientes = Arrays.asList(clienteModel);
        when(clienteService.listarTodos()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nroCliente.nroCuenta").value("1234567890"))
                .andExpect(jsonPath("$[0].nroCliente.dv").value("1"));

        verify(clienteService, times(1)).listarTodos();
    }

    @Test
    void obtenerPorId_CuandoExiste_DeberiaRetornarCliente() throws Exception {
        when(clienteService.buscarPorId(any(NroCliente.class))).thenReturn(Optional.of(clienteModel));

        mockMvc.perform(get("/api/clientes/1234567890/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nroCliente.nroCuenta").value("1234567890"))
                .andExpect(jsonPath("$.nroCliente.dv").value("1"));

        verify(clienteService, times(1)).buscarPorId(any(NroCliente.class));
    }

    @Test
    void obtenerPorId_CuandoNoExiste_DeberiaRetornar404() throws Exception {
        when(clienteService.buscarPorId(any(NroCliente.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/clientes/1234567890/1"))
                .andExpect(status().isNotFound());

        verify(clienteService, times(1)).buscarPorId(any(NroCliente.class));
    }

    @Test
    void listarActivos_DeberiaRetornarClientesActivos() throws Exception {
        List<ClienteModel> clientesActivos = Arrays.asList(clienteModel);
        when(clienteService.listarActivos()).thenReturn(clientesActivos);

        mockMvc.perform(get("/api/clientes/activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("1"));

        verify(clienteService, times(1)).listarActivos();
    }

    @Test
    void listarTodosNoActivos_DeberiaRetornarClientesConStatusCero() throws Exception {
        clienteModel.setStatus("0");
        List<ClienteModel> clientesNoActivos = Arrays.asList(clienteModel);
        when(clienteService.listarTodosNoActivos()).thenReturn(clientesNoActivos);

        mockMvc.perform(get("/api/clientes/statuscero"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("0"));

        verify(clienteService, times(1)).listarTodosNoActivos();
    }

    @Test
    void listarNoActivos_DeberiaRetornarClientesNoActivosModel() throws Exception {
        List<ClienteNoActivoModel> noActivos = Arrays.asList(clienteNoActivoModel);
        when(clienteService.listarNoActivos()).thenReturn(noActivos);

        mockMvc.perform(get("/api/clientes/noactivos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("0"));

        verify(clienteService, times(1)).listarNoActivos();
    }

    @Test
    void crear_DeberiaCrearNuevoCliente() throws Exception {
        when(clienteService.guardar(any(ClienteModel.class))).thenReturn(clienteModel);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nroCliente.nroCuenta").value("1234567890"));

        verify(clienteService, times(1)).guardar(any(ClienteModel.class));
    }

    @Test
    void actualizar_DeberiaActualizarCliente() throws Exception {
        when(clienteService.guardar(any(ClienteModel.class))).thenReturn(clienteModel);

        mockMvc.perform(put("/api/clientes/1234567890/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteModel)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nroCliente.nroCuenta").value("1234567890"));

        verify(clienteService, times(1)).guardar(any(ClienteModel.class));
    }

    @Test
    void eliminacionLogica_DeberiaEliminarClienteLogicamente() throws Exception {
        doNothing().when(clienteService).eliminacionLogica(any(NroCliente.class));

        mockMvc.perform(delete("/api/clientes/1234567890/1"))
                .andExpect(status().isNoContent());

        verify(clienteService, times(1)).eliminacionLogica(any(NroCliente.class));
    }
}
