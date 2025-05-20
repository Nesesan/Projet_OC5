package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthTokenFilterTest {

    private JwtUtils jwtUtils;
    private UserDetailsServiceImpl userDetailsService;
    private AuthTokenFilter authTokenFilter;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();

        jwtUtils = mock(JwtUtils.class);
        userDetailsService = mock(UserDetailsServiceImpl.class);

        authTokenFilter = new AuthTokenFilter();
        injectDependencies(authTokenFilter, jwtUtils, userDetailsService);
    }


    @Test
    void testDoFilterInternal_validToken_setsAuthentication() throws ServletException, IOException {
        String token = "valid.jwt.token";
        String username = "testuser";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        UserDetails userDetails = new User(username, "password", Collections.emptyList());

        when(jwtUtils.validateJwtToken(token)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertTrue(authentication instanceof UsernamePasswordAuthenticationToken);
        assertEquals(username, authentication.getName());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_invalidToken_doesNotSetAuthentication() throws ServletException, IOException {
        String token = "invalid.jwt.token";

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);

        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        when(jwtUtils.validateJwtToken(token)).thenReturn(false);

        authTokenFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, times(1)).doFilter(request, response);
    }


    private void injectDependencies(AuthTokenFilter filter, JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
        try {
            var jwtField = AuthTokenFilter.class.getDeclaredField("jwtUtils");
            jwtField.setAccessible(true);
            jwtField.set(filter, jwtUtils);

            var userServiceField = AuthTokenFilter.class.getDeclaredField("userDetailsService");
            userServiceField.setAccessible(true);
            userServiceField.set(filter, userDetailsService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
