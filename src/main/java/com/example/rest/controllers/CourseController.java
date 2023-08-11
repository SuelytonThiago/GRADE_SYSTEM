package com.example.rest.controllers;

import com.example.rest.dto.CourseResponseDto;
import com.example.rest.dto.CourseRequestDto;
import com.example.rest.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@SecurityRequirement(name = "bearerAuth")
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping
    @Operation(summary = "returns all courses")
    public ResponseEntity<List<CourseResponseDto>> findAllCourses(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "returns a course by id")
    public ResponseEntity<CourseResponseDto> findCourseById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @Operation(summary = "insert a new course")
    public ResponseEntity<Void> insertNewCourse(@RequestBody @Valid CourseRequestDto courseDto){
        service.insert(courseDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "delete a course by id")
    public ResponseEntity<Void> deleteCourseById(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addStudentToCourse/{idCourse}")
    @Operation(summary = "add a student to a course")
    public ResponseEntity<Void> insertStudentToCourse(@PathVariable Long idCourse,@RequestParam String studentName){
        service.addStudentToCourse(idCourse,studentName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/removeStudentToCourse/{idCourse}")
    @Operation(summary = "remove a user from a course")
    public ResponseEntity<Void> removeStudentToCourse(@PathVariable Long idCourse,@RequestParam String studentName){
        service.removeStudentToCourse(idCourse,studentName);
        return ResponseEntity.noContent().build();
    }
}