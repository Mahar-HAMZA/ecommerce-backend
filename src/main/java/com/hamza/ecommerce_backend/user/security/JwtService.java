package com.hamza.ecommerce_backend.user.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.expiration}")
    private Long expirationTime;

    private SecretKey secretKey;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void initSecretKey(){
        byte[] toByte=Decoders.BASE64.decode(secret);
        secretKey=Keys.hmacShaKeyFor(toByte);

    }

    public String generateToken(String email, String role){
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);
        return Jwts.builder().subject(email).claim("role", role).expiration(expirationDate).signWith(secretKey).compact();
    }

    public String extractEmail(String token){
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

}
