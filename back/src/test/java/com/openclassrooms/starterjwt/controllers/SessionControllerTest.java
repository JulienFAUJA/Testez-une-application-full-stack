package com.openclassrooms.starterjwt.controllers;

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

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @InjectMocks
    private SessionController sessionController;

    @Autowired
    private MockMvc mockMvc;

    private Session session;
    private SessionDto sessionDto;


    @Test
    @WithMockUser
    void testFindById_SessionExists() {
        // Arrange
        Long sessionId = 1L;
        Session session = SessionFixture.sessionFixture1();
        SessionDto sessionDto = sessionMapper.toDto(session);
        when(sessionService.getById(sessionId)).thenReturn(session);
        when(sessionDto).thenReturn(SessionFixture.sessionDTOFixture1());

        // Act
        ResponseEntity<?> responseEntity = sessionController.findById(sessionId.toString());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionDto, responseEntity.getBody());
        verify(sessionService, times(1)).getById(sessionId);
    }

    @Test
    void testFindById_SessionNotFound() {
        // Arrange
        Long sessionId = 1L;
        when(sessionService.getById(sessionId)).thenReturn(null);

        // Act
        ResponseEntity<?> responseEntity = sessionController.findById(sessionId.toString());

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(sessionService, times(1)).getById(sessionId);
    }

    @Disabled
    @Test
    void testFindAll() {
        // Arrange
        List<Session> sessions = Arrays.asList(SessionFixture.sessionFixture1());
        List<SessionDto> sessions_dto = Arrays.asList(SessionFixture.sessionDTOFixture1());
        when(sessionService.findAll()).thenReturn(sessions);

        List<SessionDto> sessionsDtoList = new ArrayList<>();

        for (Session session : sessions) {
            sessionsDtoList.add(sessionMapper.toDto(session));
        }

        when(sessionMapper.toDto(sessions.get(0))).thenReturn(sessions_dto.get(0));

        // Act
        ResponseEntity<?> responseEntity = sessionController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(sessionsDtoList, responseEntity.getBody());
        verify(sessionService, times(1)).findAll();
    }


}
