package com.bazar.clienteservice.controller;
import com.bazar.clienteservice.model.Cliente;
import com.bazar.clienteservice.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService cliServ;

    @GetMapping()
    public Page<Cliente> findAllClientes(@PageableDefault Pageable pageable){
        return cliServ.findAllClientes(pageable);
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity findByIdCliente(@PathVariable Long id_cliente){
        Cliente cliente = cliServ.findByIdCliente(id_cliente);

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        cliServ.requestValidation(result);

        Cliente newCli = cliServ.createCliente(cliente);

        return new ResponseEntity<>(newCli, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id_cliente}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id_cliente){

        cliServ.requestValidation(result);

        Cliente newCli = cliServ.updateCliente(cliente, id_cliente);

        return new ResponseEntity<>(newCli, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id_cliente){

        Cliente cliente = cliServ.deleteCliente(id_cliente);

        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
}
