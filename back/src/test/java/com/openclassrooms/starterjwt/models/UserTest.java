package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

    private Validator validator;

    @BeforeEach
    void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSetAndGetFields() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User();

        user.setId(1L);
        user.setEmail("test@example.com");
        user.setFirstName("Jane");
        user.setLastName("Tarzan");
        user.setPassword("password");
        user.setAdmin(true);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Tarzan");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.isAdmin()).isTrue();
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldCreateUserWithBuilder() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .id(5L)
                .email("builder@example.com")
                .firstName("Ana")
                .lastName("Smith")
                .password("builderpw")
                .admin(false)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(user.getId()).isEqualTo(5L);
        assertThat(user.getEmail()).isEqualTo("builder@example.com");
        assertThat(user.getFirstName()).isEqualTo("Ana");
        assertThat(user.getLastName()).isEqualTo("Smith");
        assertThat(user.getPassword()).isEqualTo("builderpw");
        assertThat(user.isAdmin()).isFalse();
        assertThat(user.getCreatedAt()).isEqualTo(now);
        assertThat(user.getUpdatedAt()).isEqualTo(now);
    }



    @Test
    void shouldPassValidation() {
        User user = new User("valid@example.com", "albert", "alberto", "securepwd", true);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationForInvalidEmail() {
        User user = new User("invalid-email", "Doe", "Jane", "pwd", true);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }
}
