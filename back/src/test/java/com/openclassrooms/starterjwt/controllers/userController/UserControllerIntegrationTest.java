package com.openclassrooms.starterjwt.controllers.userController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "yoga@studio.com")
    void getUserById_shouldReturnUserDto() throws Exception {

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("yoga@studio.com"))
                .andExpect(jsonPath("$.firstName").value("Admin"));
    }

    @Test
    @WithMockUser(username = "user1@example.com")
    void getUserById_shouldReturn400_whenIdInvalid() throws Exception {
        mockMvc.perform(get("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void getUserById_shouldReturn404_whenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/user/999"))
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteUser_shouldReturn401_whenUnauthorized() throws Exception {
        mockMvc.perform(delete("/api/user/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user1@example.com")
    void deleteUser_shouldReturn400_whenIdInvalid() throws Exception {
        mockMvc.perform(delete("/api/user/invalid"))
                .andExpect(status().isBadRequest());
    }
}
