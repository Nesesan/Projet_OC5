package com.openclassrooms.starterjwt.services.sessionService;

import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class SessionServiceIntegrationTest {

    @Autowired
    private SessionService sessionService;







    @Test
    void testUpdateSession() {
        Session session = sessionService.getById(1L);
        session.setName("Updated Session");
        Session updated = sessionService.update(1L, session);

        assertEquals("Updated Session", updated.getName());
    }

    @Test
    void testParticipate_success() {
        sessionService.participate(1L, 1L);
        Session session = sessionService.getById(1L);

        assertTrue(session.getUsers().stream().anyMatch(user -> user.getId() == 1L));
    }


    @Test
    void testDeleteSession() {
        sessionService.delete(2L);
        assertNull(sessionService.getById(2L));
    }

    @Test
    void testParticipate_invalidSession_throwsNotFound() {
        assertThrows(NotFoundException.class, () -> sessionService.participate(99L, 1L));
    }

    @Test
    void testParticipate_invalidUser_throwsNotFound() {
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 99L));
    }
}
