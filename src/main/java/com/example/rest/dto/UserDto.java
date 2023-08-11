package com.example.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto  {

    @NotBlank(message = "the email cannot be empty or null")
    @Email(message = "provide a valid email")
    private String email;

    @NotBlank(message = "the password cannot be empty or null")
    private String pass;

}
