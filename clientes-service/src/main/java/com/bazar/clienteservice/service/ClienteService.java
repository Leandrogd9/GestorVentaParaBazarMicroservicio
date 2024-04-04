package com.bazar.clienteservice.service;

import com.bazar.clienteservice.model.Cliente;
import com.bazar.clienteservice.respository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private ClienteRepository cliRepo;

    @Override
    public Cliente create(Cliente cliente) {
        return cliRepo.save(cliente);
    }

    @Override
    public List<Cliente> getClientes() {
        return cliRepo.findAll();
    }
}
