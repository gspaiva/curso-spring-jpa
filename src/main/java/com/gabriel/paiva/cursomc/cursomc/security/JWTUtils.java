package com.gabriel.paiva.cursomc.cursomc.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(generateExpirationTime())
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }

    private Date generateExpirationTime(){
        return new Date(System.currentTimeMillis() + Long.valueOf(expiration));
    }



}
