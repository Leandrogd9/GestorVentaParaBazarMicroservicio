package com.bazar.clienteservice.service;

import com.bazar.clienteservice.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface IClienteService {

    Page<Cliente> findAllClientes(Pageable pageable);

    Cliente findByIdCliente(Long id_cliente);

    Cliente createCliente(Cliente cliente);

    Cliente updateCliente(Cliente cliente, Long id_cliente);

    Cliente deleteCliente(Long id_cliente);

    void requestValidation (BindingResult result);

    Cliente checkExistence(Long id_cliente);
}
