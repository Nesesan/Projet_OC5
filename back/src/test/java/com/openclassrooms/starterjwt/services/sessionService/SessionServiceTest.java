package com.openclassrooms.starterjwt.services.sessionService;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionServiceTest {

    @Mock private SessionRepository sessionRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private SessionService sessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSession() {
        Session session = new Session();
        when(sessionRepository.save(session)).thenReturn(session);

        Session created = sessionService.create(session);
        assertEquals(session, created);
    }

    @Test
    void testGetByIdFound() {
        Session session = new Session();
        session.setId(1L);
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        Session result = sessionService.getById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetByIdNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
        assertNull(sessionService.getById(1L));
    }

    @Test
    void testParticipateSuccess() {
        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>());

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        sessionService.participate(1L, 2L);
        assertTrue(session.getUsers().contains(user));
        verify(sessionRepository).save(session);
    }

    @Test
    void testParticipateUserNotFound() {
        when(sessionRepository.findById(1L)).thenReturn(Optional.of(new Session()));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 2L));
    }

    @Test
    void testParticipateAlreadyParticipating() {
        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setUsers(new ArrayList<>(List.of(user)));

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 2L));
    }

    @Test
    void testNoLongerParticipateSuccess() {
        User user = new User();
        user.setId(2L);

        Session session = new Session();
        session.setId(1L);
        session.setUsers(new ArrayList<>(List.of(user)));

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        sessionService.noLongerParticipate(1L, 2L);
        assertFalse(session.getUsers().contains(user));
        verify(sessionRepository).save(session);
    }

    @Test
    void testNoLongerParticipateUserNotFound() {
        Session session = new Session();
        session.setUsers(new ArrayList<>());

        when(sessionRepository.findById(1L)).thenReturn(Optional.of(session));

        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 2L));
    }
}
