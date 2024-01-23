package com.kamil.jwtauth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    private final String secretKey = "MWE4ZTYwNTY0MjBlNWVmODMyMmY4ZWQ0YzFkODlmODllYWI5ZDFhNDVjNDg1N2IzZTU0YjEwYjUzZDZjYjM1ZA==";
    public String extractUsername(String token) {
        return getClaim(token, claims -> claims.getSubject());
    }

    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes  = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
