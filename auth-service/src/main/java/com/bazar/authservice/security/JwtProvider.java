package com.bazar.authservice.security;

import com.bazar.authservice.dto.RequestDTO;
import com.bazar.authservice.model.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    RouteValidator routeValidator;

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(AuthUser authUser){
        return Jwts.builder()
                .signWith(this.getPrivateKey(secret))
                .setSubject(String.valueOf(authUser.getId()))
                .claim("username", authUser.getUsername())
                .claim("role", authUser.getRol().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 3600*1000))
                .compact();

    }

    public Boolean validateToken(String token, RequestDTO requestDTO){
        try {
            Jwts.parser().setSigningKey(this.getPrivateKey(secret)).build().parseClaimsJws(token);
        }catch(Exception e){
            return false;
        }

        if(!isAdmin(token) && routeValidator.isAdminPath(requestDTO)){
            return false;
        }

        return true;
    }

    public String getUsernameFromToken(String token){
        try {
            return Jwts.parser().setSigningKey(this.getPrivateKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
        }catch (Exception e){
            return "bad token";
        }
    }

    private boolean isAdmin(String token){
        return Jwts.parser().setSigningKey(this.getPrivateKey(secret)).build().parseClaimsJws(token).getBody().get("role").equals("ADMIN");
    }

    private Key getPrivateKey(String secret){
        byte[] secretBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
