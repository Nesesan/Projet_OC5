package com.openclassrooms.starterjwt.security.services.userDetailsImpl;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class UserDetailsImplTest {
    @Test
    void testBuilderAndGetters() {
        UserDetailsImpl user = UserDetailsImpl.builder()
                .id(1L)
                .username("test@mail.com")
                .firstName("user")
                .lastName("One")
                .admin(true)
                .password("secure")
                .build();

        assertEquals(1L, user.getId());
        assertEquals("test@mail.com", user.getUsername());
        assertEquals("User", user.getFirstName());
        assertEquals("One", user.getLastName());
        assertTrue(user.getAdmin());
        assertEquals("secure", user.getPassword());
    }

    @Test
    void testAuthoritiesIsEmpty() {
        UserDetailsImpl user = UserDetailsImpl.builder().build();
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void testAccountNonExpired() {
        UserDetailsImpl user = UserDetailsImpl.builder().build();
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl user1 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user2 = UserDetailsImpl.builder().id(1L).build();
        UserDetailsImpl user3 = UserDetailsImpl.builder().id(2L).build();

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertNotEquals(user1, new Object());
    }
}
