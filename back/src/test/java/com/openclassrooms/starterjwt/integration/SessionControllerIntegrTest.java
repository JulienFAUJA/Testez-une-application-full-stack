package com.openclassrooms.starterjwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.fixtures.SessionFixture;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerIntegrTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @MockBean
    private SessionRepository sessionRepository;

    @Test
    @WithMockUser
    public void testFindById_SessionExists() throws Exception {
        // Créer une session fictive
        Session session = SessionFixture.sessionFixture1();
        session.setId(null);
        // Convertir la session en DTO
        SessionDto sessionDto = SessionFixture.DtoSessionFix();

        // Enregistrer le module JavaTime pour prendre en charge la sérialisation des dates
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configurer les mocks pour retourner les valeurs attendues
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        // Effectuer la requête GET vers /api/session/1
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L)) // Vérifier l'ID de la session
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Nom de la session")) // Vérifier le nom de la session
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").exists()) // Vérifier la présence de la date
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Description de la session")) // Vérifier la description
                .andExpect(MockMvcResultMatchers.jsonPath("$.teacher_id").value(1L)) // Vérifier l'ID de l'enseignant
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdAt").exists()) // Vérifier la présence de createdAt
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedAt").exists()); // Vérifier la présence de updatedAt

        // Vérifier les appels aux méthodes des mocks
        verify(sessionService, times(1)).getById(1L);
        verify(sessionMapper, times(1)).toDto(session);
    }







    @Test
    @WithMockUser
    void testFindAll() throws Exception {
        // Créer une liste de sessions fictives
        List<Session> sessions = Arrays.asList(SessionFixture.sessionFixture1(), SessionFixture.sessionFixture2());
        // Convertir les sessions en DTOs
        List<SessionDto> sessionDtos = Arrays.asList(SessionFixture.DtoSessionFix(), SessionFixture.sessionDTOFixture2());



        // Enregistrer le module JavaTime pour prendre en charge la sérialisation des dates
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configurer les mocks pour retourner les valeurs attendues
        when(sessionService.findAll()).thenReturn(sessions);
        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        // Effectuer la requête GET vers /api/session
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L)) // Vérifier l'ID de la première session
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Nom de la session")) // Vérifier le nom de la première session
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").exists()) // Vérifier la date de la première session
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Description de la session")) // Vérifier la description de la première session
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].teacher_id").value(1L)) // Vérifier l'ID de l'enseignant de la première session
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").exists()) // Vérifier la présence du champ createdAt
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].updatedAt").exists()) // Vérifier la présence du champ updatedAt
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L)) // Vérifier l'ID de la deuxième session
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Nom de la session 2")) // Vérifier le nom de la deuxième session
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].date").exists()) // Vérifier la date de la deuxième session
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value("Description de la session 2")) // Vérifier la description de la deuxième session
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].teacher_id").value(2L)); // Vérifier l'ID de l'enseignant de la deuxième session
    }





    @Test
        @WithMockUser
        void testCreate() throws Exception {

            Session session = SessionFixture.sessionFixture1();
            SessionDto sessionDto = SessionFixture.DtoSessionFix();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
            when(sessionService.create(any())).thenReturn(session);
            when(sessionMapper.toDto(session)).thenReturn(sessionDto);

            when(sessionRepository.save(session)).thenReturn(session);


            mockMvc.perform(post("/api/session")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sessionDto)))
                    .andExpect(status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Nom de la session"));
        }




        @Test
        @WithMockUser
        void testUpdateSession() throws Exception {
            Session session = SessionFixture.sessionFixture1();
            SessionDto sessionDto = SessionFixture.DtoSessionFix();
            sessionDto.setDescription("Pensez à éteindre vos téléphones");

            when(sessionService.update(eq(1L), any(Session.class))).thenReturn(session);
            when(sessionMapper.toDto(any(Session.class))).thenReturn(sessionDto);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            mockMvc.perform(MockMvcRequestBuilders.put("/api/session/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(sessionDto)))
                    .andExpect(status().isOk());
        }



    @Nested
    class DeleteSession {
        @Test
        @WithMockUser
        public void testDeleteSession() throws Exception {
            long sessionId=1L;
            Session session = SessionFixture.sessionFixture1();

            when(sessionService.getById(sessionId)).thenReturn(session);

            mockMvc.perform(delete("/api/session/1", sessionId))
                    .andExpect(status().isOk());

            verify(sessionService, times(1)).delete(sessionId);
        }
    }

    @Nested
    class PostParticipation {
        @Test
        @WithMockUser
        public void testParticipateInSession() throws Exception {
            // Mock session ID and user ID
            long sessionId = 1L;
            long userId = 2L;

            // Perform the POST request
            mockMvc.perform(post("/api/session/{id}/participate/{userId}", sessionId, userId))
                    .andExpect(status().isOk());

            // Verify that the sessionService.participate method was called with the correct session ID and user ID
            verify(sessionService, times(1)).participate(sessionId, userId);
        }
    }


    @Nested
    class DeleteParticipation {
        @Test
        @WithMockUser
        public void testNoLongerParticipateInSession() throws Exception {
            // Mock session ID and user ID
            long sessionId = 1L;
            long userId = 2L;

            // Perform the DELETE request
            mockMvc.perform(delete("/api/session/{id}/participate/{userId}", sessionId, userId))
                    .andExpect(status().isOk());

            // Verify that the sessionService.noLongerParticipate method was called with the correct session ID and user ID
            verify(sessionService, times(1)).noLongerParticipate(sessionId, userId);
        }
    }
}
