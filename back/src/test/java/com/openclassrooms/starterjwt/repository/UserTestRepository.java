package com.openclassrooms.starterjwt.repository;
import com.openclassrooms.starterjwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTestRepository extends JpaRepository<User,Long>{

}