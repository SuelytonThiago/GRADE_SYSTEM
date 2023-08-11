package com.example.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tb_teachers")
public class Teacher extends Users{

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Teacher(){
        super();
    }

    public Teacher(Long id, String name, String cpf, String email, String pass, Course course) {
        super(id, name, cpf, email, pass);
        this.course = course;
    }

    public Teacher(String name, String cpf, String email, String pass, Course course) {
        super(name, cpf, email, pass);
        this.course = course;
    }
}
