package com.bazar.ventasservice.repository;

import com.bazar.ventasservice.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clienteapi", url = "http://api-gateway/clientes")
public interface ClienteAPI {
    @GetMapping("/{id_cliente}")
    ClienteDTO findByIdCliente(@PathVariable("id_cliente") Long id_cliente);
}
