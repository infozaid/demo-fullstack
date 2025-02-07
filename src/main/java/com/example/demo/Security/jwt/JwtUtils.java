package com.example.demo.Security.jwt;

import com.example.demo.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.security.auth.Subject;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${demo.app.jwtSecret}")
    private String jwtSecret;

    @Value("${demo.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    public long getJwtExpirationMillis(){
        return Long.parseLong(jwtExpirationMs);
    }

    public String issueToken(String subject, List<String> scopes){
        return generateJwtToken(subject, Map.of("scopes",scopes));
    }

    public String generateJwtToken(String subject, Map<String,Object> claims){

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusMillis(getJwtExpirationMillis())))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSigninKey(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }
    public Claims getClaims(String token){
         Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
         return claims;
    }

    public boolean isTokenValid(String jwt, String username){
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }
    private boolean isTokenExpired(String jwt){
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }


}
