package com.example.domain.entities;
import jakarta.persistence.*;;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tb_courses")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "tb_course_students",
    joinColumns = @JoinColumn(name = "courses"),
    inverseJoinColumns = @JoinColumn(name = "students"))
    private List<Students> students = new ArrayList<>();

    public Course(String name){
        this.name = name;
    }

    public Course(Long id,String name){
        this.id = id;
        this.name = name;
    }
}
