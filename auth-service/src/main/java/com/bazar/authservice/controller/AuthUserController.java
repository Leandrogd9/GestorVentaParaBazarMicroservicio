package com.bazar.authservice.controller;

import com.bazar.authservice.dto.AuthUserDTO;
import com.bazar.authservice.dto.NewUserDTO;
import com.bazar.authservice.dto.RequestDTO;
import com.bazar.authservice.model.AuthUser;
import com.bazar.authservice.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody NewUserDTO dto) {
        return ResponseEntity.ok(authService.create(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthUserDTO dto) {
        Map<String, String> body = new HashMap<>();
        String token = authService.login(dto);
        body.put("token", token);
        body.put("message","Inicio de sesion exitoso!");
        return ResponseEntity.ok().header("Authorization","Bearer "+token).body(body);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestParam String token, @RequestBody RequestDTO requestDTO) {
        String validToken = authService.validate(token, requestDTO);
        return ResponseEntity.ok().header("Authorization","Bearer "+validToken).body(validToken);
    }
}
