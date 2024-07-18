package com.bazar.authservice.security;

import com.bazar.authservice.model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
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
        System.out.println("Esto es una prueba "+secret);
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        System.out.println("Esto es una prueba "+secret);
    }

    public String createToken(AuthUser authUser){
        return Jwts.builder()
                .signWith(this.getPrivateKey(secret))
                .setSubject(authUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3600*1000))
                .compact();

    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(this.getPrivateKey(secret)).build().parseClaimsJws(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(this.getPrivateKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return "bad token";
        }
    }

    private Key getPrivateKey(String secret){
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
