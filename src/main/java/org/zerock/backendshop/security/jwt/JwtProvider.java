package org.zerock.backendshop.security.jwt;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.zerock.backendshop.security.UserPrinciple;

public interface JwtProvider {
    String generateToken(UserPrinciple auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);

}
