package com.openclassrooms.starterjwt.security.services.userDetailsServiceImpl;

import com.openclassrooms.starterjwt.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserDetailsServiceImplIntegrationTest {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loadUsersService_existingUser_returnUserDetails() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("yoga@studio.com");

        assertNotNull(userDetails);
        assertEquals("yoga@studio.com", userDetails.getUsername());
        assertTrue(passwordEncoder.matches("test!1234", userDetails.getPassword()));
        System.out.println(userDetails.getPassword());
    }
}
