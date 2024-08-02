package com.bazar.authservice.service;

import com.bazar.authservice.dto.AuthUserDTO;
import com.bazar.authservice.dto.NewUserDTO;
import com.bazar.authservice.dto.RequestDTO;
import com.bazar.authservice.model.AuthUser;

public interface IAuthService {

    //CRUD
    AuthUser create(NewUserDTO dto);
    //AUTENTICACION
    String login(AuthUserDTO dto);
    //AUTORIZACION
    String validate(String token, RequestDTO requestDTO);

    //EXTRAS
    void checkExistence(String username, String message, String action);
}
