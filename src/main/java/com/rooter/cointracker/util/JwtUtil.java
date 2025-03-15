package com.rooter.cointracker.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private String SECRET_KEY = "704e8b15b5d8238d65ef414b50b8937c1e68d0025dea941655d5b3e116296ceafe11ae014ec4f26c2893dc9658c1bab226c8248309b9c7c9a9deb113604c9dbb1a60f758dad6e00284456f437cbbe9f995d13352156c2249d4b862654a214220c94ef522e7856e0962808d0685d42a2d2e2bbf82404205dbc8836eabc64aeb5e86a06bce1086af36be910ea03bc579c46424c0e65c057748d5744d606e918e6ba0668f2a47b6e671a9e1310f1627bb377489ece3eb66c0754f415b8678fad0f89aae0e5f2078c75f548238e72c1be144c327f12f8e94d1a3984c10eedbf2514978822696b755a09bf9a82e8edce94eacf16f6e845725158f993e3e103634267d";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(Base64.getDecoder().decode(SECRET_KEY)).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(SECRET_KEY))
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }
}
