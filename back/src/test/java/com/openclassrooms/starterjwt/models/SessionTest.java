package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private Validator validator;

    @BeforeEach
    void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidSession_passesValidation() {
        Session session = Session.builder()
                .name("Spring Security Workshop")
                .date(new Date())
                .description("Learn Spring Security")
                .users(Collections.emptyList())
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testNameCannotBeBlank() {
        Session session = Session.builder()
                .name("")
                .date(new Date())
                .description("Valid description")
                .users(Collections.emptyList())
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    void testDateCannotBeNull() {
        Session session = Session.builder()
                .name("Valid Name")
                .date(null)
                .description("Valid description")
                .users(Collections.emptyList())
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("date")));
    }

    @Test
    void testDescriptionCannotExceedMaxLength() {
        String longDescription = "a".repeat(2501);

        Session session = Session.builder()
                .name("Valid Name")
                .date(new Date())
                .description(longDescription)
                .users(Collections.emptyList())
                .build();

        Set<ConstraintViolation<Session>> violations = validator.validate(session);
        assertFalse(violations.isEmpty());

        assertTrue(violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("description")));
    }

}
