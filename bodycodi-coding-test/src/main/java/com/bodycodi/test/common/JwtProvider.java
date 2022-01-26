package com.bodycodi.test.common;

import java.util.Base64;
import java.util.Date;

import javax.annotation.PostConstruct;
import java.security.Key;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {
    @Value("spring.jwt.secret")
    private String secretKey;

    private Long tokenValidMillisecond;

    //private static final String SECRET_KEY = "aksdfk4jhak5jkjzjk1x5cjv";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        tokenValidMillisecond = 60 * 60 * 1000L;
    }

    public String createToken (String username) {
        Claims claims = Jwts.claims().setSubject(username);
        Date now = new Date();

        return  Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getSubject (String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(secretKey.getBytes())
                            .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public boolean checkToken (String token) {
        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(secretKey.getBytes())
                                .parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (JwtException e) {
            return false;
        }
    }
}
