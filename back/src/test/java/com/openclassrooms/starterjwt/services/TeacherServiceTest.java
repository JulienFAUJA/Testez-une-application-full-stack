package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;


    @Test
    void testFindAllWithEmptyList() {
        // Arrange
        when(teacherRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Teacher> teachers = teacherService.findAll();

        // Assert
        assertEquals(0, teachers.size());
    }



    @Test
    void testFindByIdWithInvalidId() {
        // Arrange
        Long id = 2L;
        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Teacher actualTeacher = teacherService.findById(id);

        // Assert
        assertEquals(null, actualTeacher);
    }

    @Test
    void testFindByIdWithNullId() {
        // Arrange & Act
        Teacher actualTeacher = teacherService.findById(null);

        // Assert
        assertNull(actualTeacher);
    }

    @Test
    void testFindByIdWithNegativeId() {
        // Arrange & Act
        Teacher actualTeacher = teacherService.findById(-1L);

        // Assert
        assertEquals(null, actualTeacher);
    }
}
