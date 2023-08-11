package com.example.domain.repositories;

import com.example.domain.entities.Course;
import com.example.domain.entities.Notes;
import com.example.domain.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface NotesRepository extends JpaRepository<Notes,Long> {

    List<Notes> findByStudents(Students students);

    Optional<Notes> findByCourseAndStudents(Course course,Students students);
}
