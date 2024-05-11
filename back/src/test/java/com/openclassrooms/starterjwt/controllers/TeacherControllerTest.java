package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.fixtures.TeacherFixture;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {
    @Mock
    private TeacherService teacherService;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherController teacherController;

    @Nested
    class FindById {
        @Test
        void testFindById_TeacherExists() {
            Long teacherId = 1L;
            Teacher teacher = new Teacher();
            when(teacherService.findById(teacherId)).thenReturn(teacher);
            when(teacherMapper.toDto(teacher)).thenReturn(TeacherFixture.teacherDTOFixture1());
            ResponseEntity<?> response = teacherController.findById(teacherId.toString());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

        @Test
        void testFindById_TeacherNotFound() {
            Long teacherId = 1L;
            when(teacherService.findById(teacherId)).thenReturn(null);
            ResponseEntity<?> response = teacherController.findById(teacherId.toString());
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }

        @Test
        void testFindById_InvalidId() {
            ResponseEntity<?> response = teacherController.findById("invalidId");
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }
    }


}
