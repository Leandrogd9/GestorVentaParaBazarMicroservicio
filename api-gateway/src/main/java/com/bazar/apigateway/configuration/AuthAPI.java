package com.bazar.apigateway.configuration;

import com.bazar.apigateway.dto.RequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authapi")
public interface AuthAPI {
    @PostMapping("/validate")
    ResponseEntity<String> validate(@RequestParam("token") String token, @RequestBody RequestDTO requestDTO);
}
