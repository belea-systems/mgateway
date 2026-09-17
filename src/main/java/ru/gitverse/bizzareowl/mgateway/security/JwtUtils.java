package ru.gitverse.bizzareowl.mgateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtils {

    private final SecretKey secretKey;

    public JwtUtils(@Value("${application.jwt.signature.secret-key}") String base64Secret) {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public boolean isValidToken(String jwtToken) {
        try {

            @SuppressWarnings("unused")
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken);

            return true;

        } catch (JwtException jwtException) {
            return false;
        }
    }

}
