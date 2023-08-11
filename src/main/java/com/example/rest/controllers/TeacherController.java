package com.example.rest.controllers;

import com.example.rest.dto.TeacherRequestDto;
import com.example.rest.dto.TeacherResponseDto;
import com.example.rest.service.TeacherService;
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
@RequestMapping("/api/teachers")
@SecurityRequirement(name = "bearerAuth")
public class TeacherController {

    @Autowired
    private TeacherService service;


    @PostMapping("/addteacher")
    @Operation(summary = "add a new teacher")
    public ResponseEntity<Void> insert(@RequestBody @Valid TeacherRequestDto teacherDto){
        service.insert(teacherDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/addstudentnote")
    @Operation(summary = "with the teacher logged in, add a note to the student of their respective subject")
    public ResponseEntity<Void> addStudentNote(@RequestParam String studentName,
                                               @RequestParam Double note,
                                               HttpServletRequest request){
        service.addStudentNote(studentName,note,request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "return all teachers")
    public ResponseEntity<List<TeacherResponseDto>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping("/updatestudentnote")
    @Operation(summary = "with the teacher logged in, it updates the student's grade for their respective subject")
    public ResponseEntity<Void> updateStudentNote(@RequestParam String studentName,
                                                  @RequestParam Double note,
                                                  HttpServletRequest request){
        service.updateStudentNote(studentName,note,request);
        return ResponseEntity.ok().build();
    }


}
