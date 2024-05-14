package com.bazar.clienteservice.service;

import com.bazar.clienteservice.exception.CheckExistenceException;
import com.bazar.clienteservice.exception.RequestException;
import com.bazar.clienteservice.model.Cliente;
import com.bazar.clienteservice.respository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
        return this.checkExistence(id_cliente);
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        return cliRepo.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente, Long id_cliente) {
        Cliente clienteToUpdate = this.checkExistence(id_cliente);

        clienteToUpdate.setNombre(cliente.getNombre());
        clienteToUpdate.setApellido(cliente.getApellido());
        clienteToUpdate.setDni(cliente.getDni());

        return cliRepo.save(clienteToUpdate);
    }

    @Override
    public Cliente deleteCliente(Long id_cliente) {
        Cliente cliente = this.checkExistence(id_cliente);

        cliRepo.deleteById(id_cliente);

        return cliente;
    }

    @Override
    public void requestValidation(BindingResult result) {
        List<String> errors;

        if (result.hasErrors()){
            errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            throw new RequestException(errors);
        }
    }

    @Override
    public Cliente checkExistence(Long id_cliente) {
        Cliente cliente = cliRepo.findById(id_cliente).orElse(null);

        if(cliente == null){
            throw new CheckExistenceException("El id del cliente ingresado no esta relacionado a ningun cliente.");
        }

        return cliente;
    }
}
