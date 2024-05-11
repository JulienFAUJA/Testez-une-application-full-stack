package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.fixtures.UserFixture;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;


    @InjectMocks
    private UserController userController;


    @Test
    public void testFindById_UserExists() {
        User user = UserFixture.userFixture1();
        UserDto userDto = UserFixture.userDTOFixture1();
        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);
        ResponseEntity<?> responseEntity = userController.findById("1");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    void testFindById_UserNotFound() {
        Long userId = 1000L;
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
