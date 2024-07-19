package com.bazar.authservice.controller;

import com.bazar.authservice.dto.AuthUserDTO;
import com.bazar.authservice.dto.RequestDTO;
import com.bazar.authservice.dto.TokenDTO;
import com.bazar.authservice.model.AuthUser;
import com.bazar.authservice.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    private AuthUserService authUserService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthUserDTO dto) {
        TokenDTO tokenDTO = authUserService.login(dto);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validate(@RequestParam String token, @RequestBody RequestDTO requestDTO) {
        TokenDTO tokenDTO = authUserService.validate(token, requestDTO);
        if (tokenDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody AuthUserDTO dto) {
        AuthUser authUser = authUserService.save(dto);
        if (authUser == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(authUser);
    }
}
