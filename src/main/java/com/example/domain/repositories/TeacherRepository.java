package com.example.domain.repositories;

import com.example.domain.entities.Course;
import com.example.domain.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByCourse(Course course);
}
