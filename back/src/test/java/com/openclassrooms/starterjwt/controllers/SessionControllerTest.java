package com.openclassrooms.starterjwt.controllers;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.fixtures.SessionFixture;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    private SessionService sessionService = mock(SessionService.class);
    private SessionMapper sessionMapper = mock(SessionMapper.class);
    private SessionController controller = new SessionController(sessionService, sessionMapper);



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
    void shouldFindById_SessionExists() {
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
    void shouldFindById_SessionNotFound() {
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

    @Test
    public void shouldCreateSession() throws ParseException {
        Session session = SessionFixture.sessionFixture1();
        session.setId(null);
        Session sessionCreated = SessionFixture.sessionFixture1();
        SessionDto sessionDto = SessionFixture.DtoSessionFix();
        when(sessionService.create(session)).thenReturn(sessionCreated);
        when(sessionMapper.toEntity(sessionDto)).thenReturn(session);
        when(sessionMapper.toDto(session)).thenReturn(sessionDto);
        ResponseEntity<?> responseEntity = controller.create(sessionDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldDeleteSessionById() throws ParseException {
        Session session = SessionFixture.sessionFixture1();
        when(sessionService.getById(1L)).thenReturn(session);
        ResponseEntity<?> responseEntity = controller.save("1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldNtDeleteByNullId() {
        String id = "1";
        when(sessionService.getById(1L)).thenReturn(null);
        ResponseEntity<?> responseEntity = controller.save(id);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotDeleteByInvalidId() {
        String id = "invalidId";
        ResponseEntity<?> responseEntity = controller.save(id);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldCreateNewParticipation() {
        ResponseEntity<?> responseEntity = controller.participate("1", "1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotCreateParticipationByInvalidSessionId() {
        ResponseEntity<?> responseEntity = controller.participate("invalidId", "1");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotCreateParticipationByInvalidUserId() {
        ResponseEntity<?> responseEntity = controller.participate("1", "invalidId");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldDeleteParticipation() {
        ResponseEntity<?> responseEntity = controller.noLongerParticipate("1", "1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotDeleteParticipationByInvalidId() {
        ResponseEntity<?> responseEntity = controller.noLongerParticipate("invalidId", "1");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldNotDeleteParticipationByInvalidUserId() {
        ResponseEntity<?> responseEntity = controller.noLongerParticipate("1", "invalidId");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }




}
