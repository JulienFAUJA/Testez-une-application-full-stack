package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.fixtures.SessionFixture;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mock.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc.*;
import org.springframework.test.web.servlet.ResultMatcher;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    private SessionService sessionService = mock(SessionService.class);
    private SessionMapper sessionMapper = mock(SessionMapper.class);
    private SessionController controller = new SessionController(sessionService, sessionMapper);


    @Autowired
    private MockMvc mockMvc;

    private Session session;
    private SessionDto sessionDto;




    @Test
    void shouldFindSessionWithValidId() throws ParseException {
        Session session = SessionFixture.SessionFix();
        SessionDto sessionDto = SessionFixture.DtoSessionFix();
        when(sessionService.getById(1L)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);

        ResponseEntity<?> responseEntity = controller.findById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionDto, responseEntity.getBody());
    }


    @Test
    @WithMockUser
    void testFindById_SessionExists() {
        // Arrange
        Long sessionId = 1L;
        Session session = SessionFixture.sessionFixture1();
        SessionDto session_dto= SessionFixture.DtoSessionFix();
        when(sessionService.getById(sessionId)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(session_dto);

        // Act
        ResponseEntity<?> responseEntity = controller.findById(sessionId.toString());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(session_dto, responseEntity.getBody());
        verify(sessionService, times(1)).getById(sessionId);
    }

    @Test
    void testFindById_SessionNotFound() {
        // Arrange
        Long sessionId = 10000000L;
        when(sessionService.getById(sessionId)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = controller.findById(sessionId.toString());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(sessionService, times(1)).getById(sessionId);
    }

    @Test
    public void shouldFindAllSessions() throws ParseException {
        List<Session> sessions = Arrays.asList(SessionFixture.sessionFixture1(), SessionFixture.sessionFixture2());
        List<SessionDto> sessionDtos = Arrays.asList(SessionFixture.DtoSessionFix(), SessionFixture.sessionDTOFixture2());

        when(sessionService.findAll()).thenReturn(sessions);

        when(sessionMapper.toDto(sessions)).thenReturn(sessionDtos);

        ResponseEntity<?> responseEntity = controller.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionDtos, responseEntity.getBody());
    }


}
