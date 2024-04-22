package com.bazar.clienteservice.controller;

import com.bazar.clienteservice.model.Cliente;
import com.bazar.clienteservice.service.IClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService cliServ;

    @GetMapping()
    public List<Cliente> findAllClientes(){
        return cliServ.findAllClientes();
    }

    @GetMapping("/{id_cliente}")
    public ResponseEntity<?> findByIdCliente(@PathVariable Long id_cliente){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente;

        try{
            cliente = cliServ.findByIdCliente(id_cliente);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar la busqueda en la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente==null){
            response.put("error", "El cliente ID: "+id_cliente.toString()+" no existe en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> createCliente(@Valid @RequestBody Cliente cliente, BindingResult result){
        Cliente newCli;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            newCli = cliServ.createCliente(cliente);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el insert a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("success message", "El cliente se registro correctamente!");
        response.put("success", newCli);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id_cliente}")
    public ResponseEntity<?> updateCliente(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id_cliente){
        Map<String, Object> response = new HashMap<>();
        Cliente newCli;

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .toList();

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try{
            newCli = cliServ.updateCliente(cliente, id_cliente);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el update a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (newCli==null){
            response.put("error", "El cliente ID: "+id_cliente.toString()+" no existe en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("success message", "El cliente se actualizo correctamente!");
        response.put("success", newCli);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/eliminar/{id_cliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable Long id_cliente){
        Map<String, Object> response = new HashMap<>();
        Cliente cliente;
        try{
            cliente = cliServ.deleteCliente(id_cliente);
        }catch (DataAccessException e){
            response.put("connection message", "Error al realizar el delete a la base de datos!");
            response.put("connection", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente==null){
            response.put("error", "El cliente ID: "+id_cliente.toString()+" no existe en la base de datos!");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("success message", "El cliente se elimino correctamente!");
        response.put("success", cliente);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
