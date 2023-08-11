package com.example.rest.service;

import com.example.domain.repositories.CourseRepository;
import com.example.domain.repositories.StudentRepository;
import com.example.domain.entities.Course;
import com.example.domain.entities.Students;
import com.example.rest.dto.CourseResponseDto;
import com.example.rest.dto.CourseRequestDto;
import com.example.rest.dto.StudentResponseDto;
import com.example.rest.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<CourseResponseDto> findAll(){
        return courseRepository.findAll().stream().map(course ->{
            CourseResponseDto courseDto = new CourseResponseDto();
            courseDto.setName(course.getName());
            courseDto.setStudents(convertList(course.getStudents()));
            return courseDto;
        }).collect(Collectors.toList());
    }

    public CourseResponseDto findById(Long id){
        return courseRepository.findById(id).map(course ->{
            CourseResponseDto courseDto = new CourseResponseDto();
            courseDto.setName(course.getName());
            courseDto.setStudents(convertList(course.getStudents()));
            return courseDto;
                })
                .orElseThrow(() -> new ObjectNotFoundException("Course not found"));
    }

    private List<StudentResponseDto> convertList(List<Students> students){
        return students.stream().map(student ->{
            StudentResponseDto studentDto = new StudentResponseDto();
            studentDto.setName(student.getName());
            return studentDto;
        }).collect(Collectors.toList());
    }

    public void insert(CourseRequestDto courseDto){
        Course course = new Course(courseDto.getName());
        courseRepository.save(course);
    }

    public void delete(Long id){
        Optional<Course> obj = courseRepository.findById(id);
        if(obj.isEmpty()){
            throw new ObjectNotFoundException("Course not found or not exists");
        }
        courseRepository.deleteById(id);
    }

    @Transactional
    public void addStudentToCourse(Long idCourse,String studentName){
        Course course = courseRepository.findById(idCourse)
                .orElseThrow(() -> new ObjectNotFoundException("Course not found"));
        Students student = studentRepository.findByName(studentName)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        course.getStudents().add(student);
        courseRepository.save(course);

    }

    @Transactional
    public void removeStudentToCourse(Long idCourse, String studentName){
        Course course = courseRepository.findById(idCourse)
                .orElseThrow(() -> new ObjectNotFoundException("Course not found"));
        Students student = studentRepository.findByName(studentName)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        course.getStudents().remove(student);
        courseRepository.save(course);
    }


}
