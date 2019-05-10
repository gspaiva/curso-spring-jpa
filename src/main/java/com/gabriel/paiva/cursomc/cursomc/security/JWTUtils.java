package com.gabriel.paiva.cursomc.cursomc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

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


    public boolean isValidToken(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            String userName = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            if(userName != null && expiration != null && now.before(expiration)){
                return true;
            }
        }
        return false;
    }

    public String getUserName(String token) {
        Claims claims = getClaims(token);
        if(claims != null){
            return claims.getSubject();
        }
        return null;
    }

    private Claims getClaims(String token) {
        try{
            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        }catch (Exception e){
            return null;
        }
    }
}
