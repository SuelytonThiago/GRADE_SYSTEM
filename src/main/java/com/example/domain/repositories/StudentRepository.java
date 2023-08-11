package com.example.domain.repositories;

import com.example.domain.entities.Students;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Students,Long> {

    Optional<Students> findByName(String name);
    Optional<Students> findByEmail(String email);

}
