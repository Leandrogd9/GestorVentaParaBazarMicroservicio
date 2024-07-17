package com.bazar.authservice.security;

import com.bazar.authservice.model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser authUser){
        Instant issuedAt = Instant.now();
        Instant expirationTime  = issuedAt.plus(Duration.ofHours(1));

        return Jwts.builder()
                .setSubject(authUser.getUsername())
                .claim("id", authUser.getId())
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expirationTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return "bad token";
        }
    }
}
