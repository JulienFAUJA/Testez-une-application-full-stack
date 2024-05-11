package com.openclassrooms.starterjwt.security;

import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class JwtUtilsIntegTest {
    @Autowired
    private JwtUtils jwtUtils;


    private final String jwtSecret = "openclassrooms";

    @Test
    void generateJwtToken_Success() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn("yoga@test.com");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        String jwtToken = jwtUtils.generateJwtToken(authentication);
        assertNotNull(jwtToken);
    }

    @Test
    public void testGetUserNameFromJwtToken_ValidToken_ReturnsUsername() {
        String username = "yoga@test.com";
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        String extractedUsername = jwtUtils.getUserNameFromJwtToken(jwtToken);
        assertEquals(username, extractedUsername);
    }

    @Test
    public void testExpiredToken() {
        String username = "testUser";
        long expiredMillis = System.currentTimeMillis() - 1000; // 1 second ago
        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(expiredMillis))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        boolean isValid = jwtUtils.validateJwtToken(jwtToken);
        assertFalse(isValid);
    }
}
