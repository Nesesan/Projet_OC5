package com.openclassrooms.starterjwt.services.teacherService;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class TeacherServiceIntegrationTest {

    @Autowired
    private TeacherService teacherService;



    @Test
    public void testFindAll() {

        List<Teacher> teachers = teacherService.findAll();

        assertNotNull(teachers);
        assertEquals(4, teachers.size());
        assertEquals("Dupont", teachers.get(2).getLastName());
        assertEquals("Jean", teachers.get(2).getFirstName());
        assertEquals("Martin", teachers.get(3).getLastName());
        assertEquals("Sophie", teachers.get(3).getFirstName());
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
