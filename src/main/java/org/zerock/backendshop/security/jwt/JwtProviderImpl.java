package org.zerock.backendshop.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.zerock.backendshop.security.UserPrinciple;
import org.zerock.backendshop.utils.SecurityUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtProviderImpl implements  JwtProvider {
    @Value("${org.zerock.jwt.secret}")
    private String JWT_SECRET;

    @Value("${org.zerock.jwt.expiration-in-ms}")
    private Long JWT_EXPIRATION_IN_MS;
    @Override
    public String generateToken(UserPrinciple auth) {
        String authorites = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(auth.getUsername())
                .claim("roles", authorites)
                .claim("userId", auth.getId())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_IN_MS))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        if(claims == null) return null;

        String username = claims.getSubject();
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!"+username);
        Long userId = claims.get("userId", Long.class);

        Set<GrantedAuthority> authorities = Arrays.stream(claims.get("roles").toString().split(","))
                .map(SecurityUtils::convertToAuthority)
                .collect(Collectors.toSet());

        UserDetails userDetails = UserPrinciple.builder()
                .username((username))
                .authorities(authorities)
                .id(userId)
                .build();
        if(username == null) {
            System.out.println("username null error");
            return null;
        }
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!"+userDetails.getUsername());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!"+userDetails.getAuthorities());

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }

    @Override
    public boolean isTokenValid(HttpServletRequest request) {
        Claims claims = extractClaims(request);

        if(claims == null) {
            System.out.println("claims null error");
            return false;
        }

        if(claims.getExpiration().before(new Date())) return false;

        return true;
    }


    private Claims extractClaims(HttpServletRequest request){
        String token = SecurityUtils.extractAuthTokenFromRequest(request);

        if(token == null) {
            System.out.println("token null error");
            return null;
        }

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
