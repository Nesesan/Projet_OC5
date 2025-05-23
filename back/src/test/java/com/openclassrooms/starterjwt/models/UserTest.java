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
    void shouldCreateUserWithRequiredArgsConstructor() {
        User user = new User("req@example.com", "Doe", "John", "pass123", false);

        assertThat(user.getEmail()).isEqualTo("req@example.com");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getFirstName()).isEqualTo("John");
        assertThat(user.getPassword()).isEqualTo("pass123");
        assertThat(user.isAdmin()).isFalse();
    }

    @Test
    void shouldCreateUserWithAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(10L, "all@example.com", "Doe", "Jane", "pw", true, now, now);

        assertThat(user.getId()).isEqualTo(10L);
        assertThat(user.getEmail()).isEqualTo("all@example.com");
        assertThat(user.getFirstName()).isEqualTo("Jane");
        assertThat(user.getLastName()).isEqualTo("Doe");
        assertThat(user.getPassword()).isEqualTo("pw");
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
    void shouldUseEqualsAndHashCodeWithId() {
        User user1 = new User(1L, "a@b.com", "Doe", "John", "pwd", false, null, null);
        User user2 = new User(1L, "diff@b.com", "Diff", "Name", "pass", true, null, null);
        User user3 = new User(2L, "a@b.com", "Doe", "John", "pwd", false, null, null);

        assertThat(user1).isEqualTo(user2); // same ID
        assertThat(user1.hashCode()).isEqualTo(user2.hashCode());

        assertThat(user1).isNotEqualTo(user3);
        assertThat(user1.hashCode()).isNotEqualTo(user3.hashCode());

        assertThat(user1).isNotEqualTo(null);
        assertThat(user1).isNotEqualTo("a string");
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
