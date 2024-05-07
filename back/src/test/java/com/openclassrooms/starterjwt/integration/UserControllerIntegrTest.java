package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.fixtures.UserFixture;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrTest {

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void findById_UserExists() throws Exception {
        User user = UserFixture.userFixture1();
        user.setEmail("yoga@test.com");

        UserDto userDto = UserFixture.userDTOFixture1();
        userDto.setEmail("yoga@test.com");

        when(userService.findById(1L)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yoga@studio.com"));
    }

    @Test
    @WithMockUser
    void findById_UserNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    void findById_InvalidId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Disabled
    @Test
    @WithMockUser
    void saveUser_UserExists() throws Exception {
        User user = UserFixture.userFixture1();
        UserDto userDto = UserFixture.userDTOFixture1();

        when(userMapper.toEntity(userDto)).thenReturn(user);
        //when(userService.save(user)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .content("{ \"id\": 1, \"email\": \"yoga@test.com\", \"lastName\": \"Doe\", \"firstName\": \"John\", \"admin\": false }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("yoga@test.com"));
    }

    @Disabled
    @Test
    @WithMockUser
    void deleteUser_UserExists() throws Exception {
        User user = UserFixture.userFixture1();

        when(userService.findById(1L)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser
    void deleteUser_UserNotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user/10000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}