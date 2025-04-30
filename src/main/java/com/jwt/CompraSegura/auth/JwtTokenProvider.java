package com.jwt.CompraSegura.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider { //cria, valida e extrai infos

    private String jwtSecret = "queroestagio" ;
    private long jwtExpiration = 86400000 ;

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public boolean validateToken(String token){ // validar token
        try{
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseClaimsJwt(token)
                    .getBody()
                    .getSubject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    // Extrair o nome de usuário (email) do Token
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); //email(username)
    }


}
