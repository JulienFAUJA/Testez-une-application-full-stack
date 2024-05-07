package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.controllers.UserController;
import com.openclassrooms.starterjwt.fixtures.UserFixture;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserController userController;


    @Disabled("Pour tester ce tag...")
    @Test
    public void testFindById_UserExists() {
        // Arrange
        Long userId = 1L;
        User user = UserFixture.userFixture1();
        User user2 = userService.findById(userId);
        when(userMapper.toDto(user2)).thenReturn(UserFixture.userDTOFixture1());

        // Act
        ResponseEntity<?> responseEntity = userController.findById(userId.toString());

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(user, responseEntity.getBody());
        verify(userService, times(1)).findById(userId);
    }

    @Test
    void testFindById_UserNotFound() {
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(null);

        ResponseEntity<?> response = userController.findById(userId.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindById_InvalidId() {
        ResponseEntity<?> response = userController.findById("invalidId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void testDeleteUser_UserDoesNotExist() {
        Long userId = 99999L;
        when(userService.findById(userId)).thenReturn(null);

        ResponseEntity<?> response = userController.save(userId.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, never()).delete(any());
    }

    @Test
    void testDeleteUser_InvalidId() {
        ResponseEntity<?> response = userController.save("invalidId");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(userService, never()).delete(any());
    }

}
