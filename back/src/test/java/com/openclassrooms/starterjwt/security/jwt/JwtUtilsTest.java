package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.security.core.Authentication;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private final String jwtSecret = "testSecretKey123456789";
    private final int jwtExpirationMs = 1000 * 60;

    @BeforeEach
    void setUp() throws Exception {
        jwtUtils = new JwtUtils();

        Field secretField = JwtUtils.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtUtils, jwtSecret);

        Field expirationField = JwtUtils.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtUtils, jwtExpirationMs);
    }

    @Test
    void testGenerateAndValidateJwtToken() {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(userDetails.getUsername()).thenReturn("testuser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        String token = jwtUtils.generateJwtToken(authentication);

        assertNotNull(token);
        assertTrue(jwtUtils.validateJwtToken(token));
        assertEquals("testuser", jwtUtils.getUserNameFromJwtToken(token));
    }

    @Test
    void testValidateJwtToken_withInvalidToken() {
        String invalidToken = "this.is.not.a.valid.token";
        assertFalse(jwtUtils.validateJwtToken(invalidToken));
    }

    @Test
    void testValidateJwtToken_withExpiredToken() throws InterruptedException {

        String expiredToken = io.jsonwebtoken.Jwts.builder()
                .setSubject("testuser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 60000))
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        assertFalse(jwtUtils.validateJwtToken(expiredToken));
    }
}
