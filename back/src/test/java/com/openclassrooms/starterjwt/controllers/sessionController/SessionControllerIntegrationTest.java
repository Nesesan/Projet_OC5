package com.openclassrooms.starterjwt.controllers.sessionController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    SessionService sessionService;


    @Test
    @WithMockUser(username = "yoga@studio.com")
    void testFindAllSessions() throws Exception {
        mockMvc.perform(get("/api/session"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testFindSessionWithId() throws Exception {
        mockMvc.perform(get("/api/session/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new session"))
                .andExpect(jsonPath("$.description").value("new description"))
                .andExpect(jsonPath("$.teacher_id").value(1));
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testFindSessionBadRequest() throws Exception {
        mockMvc.perform(get("/api/session/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testCreateSession() throws Exception {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("new session");
        sessionDto.setDescription("new description");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);

        mockMvc.perform(post("/api/session")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new session"))
                .andExpect(jsonPath("$.description").value("new description"))
                .andExpect(jsonPath("$.teacher_id").value(1));
    }

    @Test
    @WithMockUser("lounss@mail.com")
    void testCreateSession_InvalidInput() throws Exception {
        SessionDto dto = new SessionDto();
        dto.setDescription("Session sans nom");
        dto.setDate(new Date());
        dto.setTeacher_id(1L);

        mockMvc.perform(post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testUpdateSession() throws Exception {


        Session sessionUpdated = sessionService.getById(1L);
        System.out.println(sessionUpdated);
        sessionUpdated.setName("new session");
        sessionUpdated.setDescription("new description");


        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(sessionUpdated.getId());
        sessionDto.setName(sessionUpdated.getName());
        sessionDto.setDescription(sessionUpdated.getDescription());
        sessionDto.setTeacher_id(sessionUpdated.getTeacher() != null ? sessionUpdated.getTeacher().getId() : null);

        sessionDto.setDate(new Date());


        mockMvc.perform(put("/api/session/{id}", sessionUpdated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new session"))
                .andExpect(jsonPath("$.description").value("new description"))
                .andExpect(jsonPath("$.teacher_id").value(sessionUpdated.getTeacher() != null ? sessionUpdated.getTeacher().getId() : null));
    }




    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testParticipateWithPreloadedUser() throws Exception {
        mockMvc.perform(post("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "lounss@mail.com")
    void testUnparticipateWithPreloadedUser() throws Exception {
        mockMvc.perform(delete("/api/session/1/participate/2"))
                .andExpect(status().isOk());
    }
}
