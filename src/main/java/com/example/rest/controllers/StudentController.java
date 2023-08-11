package com.example.rest.controllers;

import com.example.rest.dto.NotesResponseDto;
import com.example.rest.dto.StudentRequestDto;
import com.example.rest.dto.StudentResponseDto;
import com.example.rest.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@SecurityRequirement(name = "bearerAuth")
public class StudentController {

    @Autowired
    private StudentService service;


    @PostMapping
    @Operation(summary = "add a new student")
    public ResponseEntity<Void> insert(@RequestBody @Valid StudentRequestDto studentsDto){
        service.insert(studentsDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getnotes")
    @Operation(summary = "returns all grades for the logged in student")
    public ResponseEntity<List<NotesResponseDto>> findNotesByStudentName(HttpServletRequest request){
        return ResponseEntity.ok(service.findAllStudentsNotes(request));
    }

    @GetMapping("/getallstudents")
    @Operation(summary = "returns all students ")
    public ResponseEntity<List<StudentResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }
}
