package com.bazar.clienteservice.service;

import com.bazar.clienteservice.model.Cliente;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface IClienteService {

    List<Cliente> findAllClientes();

    Cliente findByIdCliente(Long id_cliente);

    Cliente createCliente(Cliente cliente);

    Cliente updateCliente(Cliente cliente, Long id_cliente);

    Cliente deleteCliente(Long id_cliente);

    void requestValidation (BindingResult result);

    Cliente checkExistence(Long id_cliente);
}
