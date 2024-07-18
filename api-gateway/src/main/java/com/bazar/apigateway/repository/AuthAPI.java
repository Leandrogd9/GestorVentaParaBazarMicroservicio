package com.bazar.apigateway.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authApi", url = "localhost:443/auth-service/auth")
public interface AuthAPI {
    @PostMapping("/validate")
    ResponseEntity<?> validate(@RequestParam("token") String token);
}
