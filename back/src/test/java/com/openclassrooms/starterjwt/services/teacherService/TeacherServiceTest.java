package com.openclassrooms.starterjwt.services.teacherService;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Teacher teacher1 = new Teacher(3L, "Dupont", "Jean", LocalDateTime.now(), LocalDateTime.now());
        Teacher teacher2 = new Teacher(4L, "Curie", "Marie", LocalDateTime.now(), LocalDateTime.now());

        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));
        when(teacherRepository.findById(3L)).thenReturn(java.util.Optional.of(teacher1));
    }

    @Test
    public void testFindAll() {
        List<Teacher> teachers = teacherService.findAll();

        assertNotNull(teachers);
        assertEquals(2, teachers.size());
        assertEquals("Dupont", teachers.get(0).getLastName());
        assertEquals("Jean", teachers.get(0).getFirstName());
        assertEquals("Curie", teachers.get(1).getLastName());
        assertEquals("Marie", teachers.get(1).getFirstName());
    }

    @Test
    public void testFindById() {
        Teacher teacher = teacherService.findById(3L);

        assertNotNull(teacher);
        assertEquals(3, teacher.getId());
        assertEquals("Dupont", teacher.getLastName());
        assertEquals("Jean", teacher.getFirstName());
    }
}
