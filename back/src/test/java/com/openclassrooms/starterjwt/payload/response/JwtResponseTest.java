package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtResponseTest {

    @Test
    void shouldCreateJwtResponseWithConstructor() {
        JwtResponse response = new JwtResponse(
                "testToken",
                1L,
                "user@example.com",
                "Albert",
                "Einstein",
                true
        );

        assertThat(response.getToken()).isEqualTo("testToken");
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getUsername()).isEqualTo("user@example.com");
        assertThat(response.getFirstName()).isEqualTo("Albert");
        assertThat(response.getLastName()).isEqualTo("Einstein");
        assertThat(response.getAdmin()).isTrue();
        assertThat(response.getType()).isEqualTo("Bearer");
    }

    @Test
    void shouldSetAndGetFields() {
        JwtResponse response = new JwtResponse("token", 2L, "login", "Jane", "Smith", false);

        response.setToken("newToken");
        response.setUsername("newuser@example.com");
        response.setFirstName("UpdatedFirst");
        response.setLastName("UpdatedLast");
        response.setId(3L);
        response.setAdmin(true);
        response.setType("Custom");

        assertThat(response.getToken()).isEqualTo("newToken");
        assertThat(response.getUsername()).isEqualTo("newuser@example.com");
        assertThat(response.getFirstName()).isEqualTo("UpdatedFirst");
        assertThat(response.getLastName()).isEqualTo("UpdatedLast");
        assertThat(response.getId()).isEqualTo(3L);
        assertThat(response.getAdmin()).isTrue();
        assertThat(response.getType()).isEqualTo("Custom");
    }
}
