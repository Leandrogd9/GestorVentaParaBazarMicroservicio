package com.bazar.authservice.service;

import com.bazar.authservice.configuration.RouteValidator;
import com.bazar.authservice.dto.AuthUserDTO;
import com.bazar.authservice.dto.NewUserDTO;
import com.bazar.authservice.dto.RequestDTO;
import com.bazar.authservice.enums.Rol;
import com.bazar.authservice.exception.CheckExistenceException;
import com.bazar.authservice.exception.InvalidToken;
import com.bazar.authservice.exception.LoginFailure;
import com.bazar.authservice.jwt.IJwtService;
import com.bazar.authservice.model.AuthUser;
import com.bazar.authservice.repository.AuthUserRepository;
import jakarta.ws.rs.NotAuthorizedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    private static final String FOUND = "El usuario que desea %s ya existe.";
    private static final String UNAUTHORIZED = "Su credenciales de inicio de sesion no son correctas.";

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private RouteValidator routeValidator;

    @Override
    public AuthUser create(NewUserDTO dto) {
        checkExistence(dto.getUsername(), FOUND, "registrar");
        AuthUser authUser = AuthUser.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .rol(Rol.valueOf(dto.getRol().toUpperCase()))
                .build();

        return authUserRepository.save(authUser);
    }

    @Override
    public String login(AuthUserDTO dto) {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(dto.getUsername());

        if (authUser.isEmpty()) {
            throw new LoginFailure(UNAUTHORIZED);
        }

        if (!passwordEncoder.matches(dto.getPassword(), authUser.get().getPassword())) {
            throw new LoginFailure(UNAUTHORIZED);
        }

        return jwtService.createToken(authUser.get());
    }

    @Override
    public String validate(String token, RequestDTO requestDTO) {
        jwtService.validateToken(token);

        if (routeValidator.isAdminPath(requestDTO) && !jwtService.isAdmin(token)){
                throw new InvalidToken("No tienes permiso para acceder.");
        }

        return token;
    }

    @Override
    public void checkExistence(String username, String message, String action) {
        Optional<AuthUser> authUser = authUserRepository.findByUsername(username);
        if (authUser.isPresent()) {
            throw new CheckExistenceException(String.format(FOUND, action));
        }
    }
}
