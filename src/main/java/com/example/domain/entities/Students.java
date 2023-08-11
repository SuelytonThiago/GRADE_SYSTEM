package com.example.domain.entities;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "tb_students")
public class Students extends Users{

    public Students(){
        super();
    }

    public Students(Long id, String name, String cpf, String email, String pass) {
        super(id, name, cpf, email, pass);
    }

    public Students(String name, String cpf, String email, String pass) {
        super(name, cpf, email, pass);
    }
}
