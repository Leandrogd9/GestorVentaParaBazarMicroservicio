package com.bazar.clienteservice.service;

import com.bazar.clienteservice.model.Cliente;

import java.util.List;

public interface IClienteService {
    public Cliente create(Cliente cliente);

    public List<Cliente> getClientes();
}
