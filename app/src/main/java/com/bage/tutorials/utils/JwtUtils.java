package com.bage.tutorials.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtParser;

public class JwtUtils {

        public static String compactJws(String sub, SignatureAlgorithm alg, String key) {
        return Jwts.builder().setSubject(sub).signWith(alg, key).compact();

    }

    public static String compactJws(String sub, String key) {
        return compactJws(sub, SignatureAlgorithm.HS512, key);

    }

    public static Jws<Claims> parse(String claimsJws, String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(claimsJws);

    }

    public static Claims decodeTokenClaims(String token) {
        String[] splitToken = token.split("\\.");
        String unsignedToken = splitToken[0] + "." + splitToken[1] + ".";

        DefaultJwtParser parser = new DefaultJwtParser();
        Jwt<?, ?> jwt = parser.parse(unsignedToken);
        Claims claims = (Claims) jwt.getBody();
        return claims;
    }

}
