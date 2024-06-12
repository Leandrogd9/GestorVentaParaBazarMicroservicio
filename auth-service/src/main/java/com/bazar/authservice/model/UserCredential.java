package com.bazar.authservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usersCredentials")
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "no puede estar en blanco este registro.")
    private String username;
    @NotBlank(message = "no puede estar en blanco este registro.")
    @Email(message = "el formato no es el correcto.")
    private String email;
    @NotBlank(message = "no puede estar en blanco este registro.")
    private String password;
}
