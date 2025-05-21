package com.openclassrooms.starterjwt.controllers.userController;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserService userService;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userMapper = mock(UserMapper.class);
        userController = new UserController(userService, userMapper);
    }
    @Test
    void findById_shouldReturnUserDto_whenUserExists() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@mail.com");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("user@mail.com");

        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<?> response = userController.findById("1");

        assertEquals(200, response.getStatusCodeValue());
        assertInstanceOf(UserDto.class, response.getBody());

        UserDto result = (UserDto) response.getBody();
        assertEquals(1L, result.getId());
        assertEquals("user@mail.com", result.getEmail());
    }

    @Test
    void findById_shouldReturnNotFound_whenUserNotFound() {
        when(userService.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = userController.findById("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void delete_shouldReturnOk_whenAuthorized() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@mail.com");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user@mail.com");

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, null)
        );

        when(userService.findById(1L)).thenReturn(user);

        ResponseEntity<?> response = userController.save("1");

        assertEquals(200, response.getStatusCodeValue());
        verify(userService).delete(1L);
    }

    @Test
    void delete_shouldReturnNotFound_whenUserDoesNotExist() {
        when(userService.findById(1L)).thenReturn(null);

        ResponseEntity<?> response = userController.save("1");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void delete_shouldReturnBadRequest_whenIdInvalid() {
        ResponseEntity<?> response = userController.save("abc");

        assertEquals(400, response.getStatusCodeValue());
    }
}
