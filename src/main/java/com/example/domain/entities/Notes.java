package com.example.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_notes")
public class Notes implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double note;

    @ManyToOne
    @JoinColumn(name = "idcourse")
    private Course course;


    @ManyToOne
    @JoinColumn(name = "idstudent")
    private Students students;

    public Notes(Double note, Course course,Students students){
        this.note = note;
        this.course = course;
        this.students = students;
    }

}
