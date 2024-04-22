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
    public List<Cliente> findAllClientes() {
        return cliRepo.findAll();
    }

    @Override
    public Cliente findByIdCliente(Long id_cliente) {
        return cliRepo.findById(id_cliente).orElse(null);
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        return cliRepo.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente, Long id_cliente) {
        Cliente clienteToUpdate = this.findByIdCliente(id_cliente);

        if (clienteToUpdate==null){
            return null;
        }

        clienteToUpdate.setNombre(cliente.getNombre());
        clienteToUpdate.setApellido(cliente.getApellido());
        clienteToUpdate.setDni(cliente.getDni());

        return cliRepo.save(clienteToUpdate);
    }

    @Override
    public Cliente deleteCliente(Long id_cliente) {
        Cliente cliente = this.findByIdCliente(id_cliente);

        cliRepo.deleteById(id_cliente);

        return cliente;
    }
}
