package com.bazar.authservice.jwt;
import com.bazar.authservice.model.AuthUser;
import javax.crypto.SecretKey;

public interface IJwtService {
    //PRINCIPAL
    String createToken(AuthUser user);
    void validateToken(String token);

    //EXTRAS
    SecretKey getPrivateKey();
    boolean isAdmin(String token);
}
