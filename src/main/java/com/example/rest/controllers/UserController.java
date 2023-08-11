package com.example.rest.controllers;

import com.example.rest.dto.AdministratorRequestDto;
import com.example.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/addAdm")
    @Operation(summary = "add a new admin")
    public ResponseEntity<Void> insertANewAdm(@RequestBody @Valid AdministratorRequestDto administrator){
        userService.insertNewAdmin(administrator);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
