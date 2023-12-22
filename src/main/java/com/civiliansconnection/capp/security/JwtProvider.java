package com.civiliansconnection.capp.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    private final ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String jwtSecret = "195ff8bf66a02f18495baf95e2516bbf60227b90b2989681e4ef14da939d818773d59842054d42eaf525955e294c27a157ea222ac60fe370afea243126d56515";

    @Value("${jwt.expirationInMs}")
    private int jwtExpirationInMs = 604800000;

    MacAlgorithm alg = Jwts.SIG.HS512; //or HS384 or HS256
//    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public JwtProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String generateToken(CivUserDetails userDetails) throws JsonProcessingException {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        String json = objectMapper.writeValueAsString(userDetails);
        return Jwts.builder()
                .signWith(key, alg)
                .subject(json)
                .issuedAt(now)
                .expiration(expiryDate)
                .compact();
    }

    public String extractUsername(String token) throws JsonProcessingException {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        String jsonSubject = claims.getSubject();
        CivUserDetails civUserDetails = objectMapper.readValue(jsonSubject, CivUserDetails.class);


        return civUserDetails.getUsername();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
