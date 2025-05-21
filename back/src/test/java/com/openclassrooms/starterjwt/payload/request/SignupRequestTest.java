package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SignupRequestTest {

    private Validator validator;

    @BeforeEach
    void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassValidationWithValidFields() {
        SignupRequest signup = new SignupRequest();
        signup.setEmail("test@example.com");
        signup.setFirstName("Alice");
        signup.setLastName("Martin");
        signup.setPassword("password123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signup);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldFailValidationWithInvalidFields() {
        SignupRequest signup = new SignupRequest();
        signup.setEmail("invalid-email");
        signup.setFirstName("Al");
        signup.setLastName("");
        signup.setPassword("123");

        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signup);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("firstName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lastName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        SignupRequest s1 = new SignupRequest();
        s1.setEmail("same@example.com");
        s1.setFirstName("Jane");
        s1.setLastName("albert");
        s1.setPassword("password123");

        SignupRequest s2 = new SignupRequest();
        s2.setEmail("same@example.com");
        s2.setFirstName("Jane");
        s2.setLastName("albert");
        s2.setPassword("password123");

        SignupRequest s3 = new SignupRequest();
        s3.setEmail("same@example.com");
        s3.setFirstName("Jane");
        s3.setLastName("albert");
        s3.setPassword("differentPassword");

        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());

        assertThat(s1).isNotEqualTo(s3);
        assertThat(s1.hashCode()).isNotEqualTo(s3.hashCode());

        assertThat(s1).isNotEqualTo(null);
        assertThat(s1).isNotEqualTo("string");
    }

    @Test
    void shouldTestToString() {
        SignupRequest signup = new SignupRequest();
        signup.setEmail("toString@example.com");
        signup.setFirstName("Johnny");
        signup.setLastName("Bgood");
        signup.setPassword("securepass");

        String result = signup.toString();

        assertThat(result)
                .contains("email=toString@example.com")
                .contains("firstName=Johnny")
                .contains("lastName=Bgood")
                .contains("password=securepass");
    }
}
