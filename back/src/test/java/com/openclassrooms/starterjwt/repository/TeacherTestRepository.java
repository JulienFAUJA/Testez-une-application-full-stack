package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherTestRepository extends JpaRepository<Teacher,Long>{
}
