package com.example.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequestDto {

    @NotBlank(message = "the name cannot be empty or null")
    private String name;

    @NotBlank(message = "the email cannot be empty or null")
    @Email(message = "provide a valid email")
    private String email;

    @NotBlank(message = "the cpf cannot be empty or null")
    @CPF(message = "provide a valid cpf")
    private String cpf;

    @NotBlank(message = "the pass cannot be empty or null")
    private String pass;

    @NotBlank(message = "the name of course cannot be empty or null")
    private String courseName;
}
