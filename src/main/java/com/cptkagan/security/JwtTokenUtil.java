package com.cptkagan.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component

public class JwtTokenUtil {
    private final String secret = "3viXNPoEKeapyXbHoTnL+/oDjbun6XC3gAu8PKbIt98=";
    private final long expiration = 36000000;

    public String generateToken(String username){
        return Jwts.builder()
                   .setSubject(username) // Saklanacak Ana Bilgi
                   .setIssuedAt(new Date()) // Ne zaman oluşturuldu
                   .setExpiration(new Date(System.currentTimeMillis()+expiration)) // Ne zaman bitecek
                   .signWith(SignatureAlgorithm.HS256, secret) // Anahtar ile imzalama
                   .compact(); // Parçaları birleştirir (Header, Payload, Signature)

    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret) // Doğru gizli anahtar kullanılıyor mu kontrol et.
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null; // Hatalı token durumda null dönebilir.
        }
    }
}
