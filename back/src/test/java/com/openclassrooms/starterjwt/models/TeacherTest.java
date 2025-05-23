package com.openclassrooms.starterjwt.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TeacherTest {

    private Validator validator;

    @BeforeEach
    void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldValidateValidTeacher() {
        Teacher teacher = new Teacher()
                .setFirstName("Alice")
                .setLastName("Martin");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        assertThat(violations).isEmpty();
    }

    @Test
    void shouldInvalidateBlankFields() {
        Teacher teacher = new Teacher()
                .setFirstName("")
                .setLastName("");

        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);
        assertThat(violations).hasSize(2);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("firstName"));
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("lastName"));
    }


    @Test
    void shouldUseBuilderCorrectly() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(teacher.getId()).isEqualTo(2L);
        assertThat(teacher.getFirstName()).isEqualTo("Jane");
        assertThat(teacher.getLastName()).isEqualTo("Smith");
        assertThat(teacher.getCreatedAt()).isEqualTo(now);
        assertThat(teacher.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldUseAllArgsConstructor() {
        LocalDateTime created = LocalDateTime.of(2023, 1, 1, 10, 0);
        LocalDateTime updated = LocalDateTime.of(2024, 5, 1, 15, 30);

        Teacher teacher = new Teacher(1L, "Doe", "John", created, updated);

        assertThat(teacher.getId()).isEqualTo(1L);
        assertThat(teacher.getLastName()).isEqualTo("Doe");
        assertThat(teacher.getFirstName()).isEqualTo("John");
        assertThat(teacher.getCreatedAt()).isEqualTo(created);
        assertThat(teacher.getUpdatedAt()).isEqualTo(updated);
    }

    @Test
    void shouldUseSettersAndGetters() {
        Teacher teacher = new Teacher();
        LocalDateTime now = LocalDateTime.now();

        teacher.setId(5L);
        teacher.setFirstName("First");
        teacher.setLastName("Name");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        assertThat(teacher.getId()).isEqualTo(5L);
        assertThat(teacher.getFirstName()).isEqualTo("First");
        assertThat(teacher.getLastName()).isEqualTo("Name");
        assertThat(teacher.getCreatedAt()).isEqualTo(now);
        assertThat(teacher.getUpdatedAt()).isEqualTo(now);
    }

    @Test
    void shouldHaveValidToString() {
        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .id(10L)
                .firstName("ToString")
                .lastName("Test")
                .createdAt(now)
                .updatedAt(now)
                .build();

        String toString = teacher.toString();

        assertThat(toString).contains("ToString");
        assertThat(toString).contains("Test");
        assertThat(toString).contains("10");
        assertThat(toString).contains(now.toString());
    }

    @Test
    void shouldUseEqualsAndHashCodeWithSameId() {
        Teacher t1 = new Teacher(1L, "Smith", "Alice", null, null);
        Teacher t2 = new Teacher(1L, "Dupont", "Bob", null, null); // même id, autres champs différents

        assertThat(t1).isEqualTo(t2);
        assertThat(t1.hashCode()).isEqualTo(t2.hashCode());
    }

    @Test
    void shouldNotBeEqualIfIdsDiffer() {
        Teacher t1 = new Teacher(1L, "Smith", "Alice", null, null);
        Teacher t2 = new Teacher(2L, "Smith", "Alice", null, null);

        assertThat(t1).isNotEqualTo(t2);
        assertThat(t1.hashCode()).isNotEqualTo(t2.hashCode());
    }

    @Test
    void shouldNotBeEqualToNullOrDifferentType() {
        Teacher teacher = new Teacher(1L, "Smith", "Alice", null, null);

        assertThat(teacher.equals(null)).isFalse();             // null
        assertThat(teacher.equals("not a teacher")).isFalse();  // autre type
    }

    @Test
    void shouldRespectCanEqualContract() {
        Teacher teacher1 = new Teacher(1L, "Smith", "Alice", null, null);
        Teacher teacher2 = new Teacher(1L, "Smith", "Alice", null, null);

        assertThat(teacher1.canEqual(teacher2)).isTrue();
        assertThat(teacher1.canEqual("string")).isFalse(); // test explicite de canEqual
    }

}
