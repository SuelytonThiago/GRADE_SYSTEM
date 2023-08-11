package com.example.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "tb_administrators")
public class Administrator extends Users{

    public Administrator(){
        super();
    }

    public Administrator(Long id, String name, String cpf, String email, String pass) {
        super(id, name, cpf, email, pass);
    }

    public Administrator(String name, String cpf, String email, String pass) {
        super(name, cpf, email, pass);
    }
}
