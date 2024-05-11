package com.openclassrooms.starterjwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.fixtures.TeacherFixture;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherTestRepository;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TeacherControllerIntegrTest {
    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;

    @MockBean
    private TeacherTestRepository teacherTestRepository;



    @AfterEach
    void cleanDataBase() {
        teacherTestRepository.deleteAll();
    }
    @Test
    @WithMockUser(roles = "USER")
    void shouldGetTeacherById() throws Exception {
        Teacher teacher = TeacherFixture.teacherFixture1();
        teacher.setId(null);
        TeacherDto teacherDto = TeacherFixture.teacherDTOFixture1();
        Long id =1L;
        // Enregistrer le module JavaTime pour prendre en charge la sérialisation des dates
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        // Configurer les mocks pour retourner les valeurs attendues
        when(teacherService.findById(id)).thenReturn(teacher);
        when(teacherMapper.toDto(teacher)).thenReturn(teacherDto);
        when(teacherTestRepository.save(teacher)).thenReturn(teacher);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/"+id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Margot")));
    }
    @Test
    @WithMockUser(roles = "USER")
    void shouldNotGetTeacherByIdWhenNotFound() throws Exception {
        Long id = 1000L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/"+id))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
