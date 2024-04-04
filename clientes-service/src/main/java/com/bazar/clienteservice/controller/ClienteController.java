package com.bazar.clienteservice.controller;

import com.bazar.clienteservice.model.Cliente;
import com.bazar.clienteservice.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService cliServ;

    @GetMapping("/traer")
    public List<Cliente> getClientes(){
        return cliServ.getClientes();
    }
    @PostMapping("/clear")
    public Cliente create(@RequestBody Cliente cliente){
        return cliServ.create(cliente);
    }
}
