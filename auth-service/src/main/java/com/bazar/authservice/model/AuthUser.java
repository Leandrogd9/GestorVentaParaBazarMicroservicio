package com.bazar.authservice.model;

import com.bazar.authservice.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "no puede estar en blanco este registro.")
    private String username;
    @NotBlank(message = "no puede estar en blanco este registro.")
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol role;
}