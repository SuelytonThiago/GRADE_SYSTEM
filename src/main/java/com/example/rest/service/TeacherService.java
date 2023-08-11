package com.example.rest.service;

import com.example.config.security.JWTService;
import com.example.domain.entities.*;
import com.example.domain.enums.Roles;
import com.example.domain.repositories.*;
import com.example.rest.dto.TeacherRequestDto;
import com.example.rest.dto.TeacherResponseDto;
import com.example.rest.service.exceptions.CustomException;
import com.example.rest.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Transactional
    public void insert(TeacherRequestDto teacherDto){
        Course course = courseRepository.findByName(teacherDto.getCourseName())
                .orElseThrow(() -> new ObjectNotFoundException("Course not found"));
        var obj = teacherRepository.findByCourse(course);
        if(!obj.isEmpty()){
            throw new CustomException("there cannot be two or more professors for a single course");
        }

        Roles roles = Roles.nameOf("TEACHER");
        Role role = roleRepository.findByRoleName(roles).get();
        Teacher teacher = new Teacher();
        teacher.setName(teacherDto.getName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPass(encoder.encode(teacherDto.getPass()));
        teacher.setCpf(teacherDto.getCpf());
        teacher.setCourse(course);
        teacherRepository.save(teacher);
        teacher.getRoles().add(role);
    }

    public List<TeacherResponseDto> findAll(){
        return teacherRepository.findAll().stream().map(teacher -> {
            TeacherResponseDto teacherDto = new TeacherResponseDto();
            teacherDto.setTeacherName(teacher.getName());
            teacherDto.setCourseName(teacher.getCourse().getName());
            return teacherDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public void updateStudentNote(String studentName, Double note, HttpServletRequest request) {
        var auth = request.getHeader("Authorization");
        var token = auth.replace("Bearer ", "");
        var email = jwtService.getSubject(token);
        var teacher = teacherRepository.findByEmail(email).get();
        Students student = studentRepository.findByName(studentName)
                .orElseThrow(() -> new ObjectNotFoundException("Student not found by name: " + studentName));
        Notes noteStudent = notesRepository.findByCourseAndStudents(teacher.getCourse(), student)
                .orElseThrow(() -> new ObjectNotFoundException("There is no student grade for this course to be updated"));
        noteStudent.setNote(note);
        notesRepository.save(noteStudent);
    }

    @Transactional
    public void addStudentNote(String studentName,Double note, HttpServletRequest request){
        var auth = request.getHeader("Authorization");
        var token = auth.replace("Bearer ","");
        var email = jwtService.getSubject(token);
        var teacher = teacherRepository.findByEmail(email).get();
        Students students = studentRepository.findByName(studentName)
                .orElseThrow(() -> new ObjectNotFoundException("Student not found or not registered"));
        var obj = notesRepository.findByCourseAndStudents(teacher.getCourse(),students);
        if(!obj.isEmpty()){
            throw new CustomException("there is already a note for this course for the student " + studentName);
        }
        Notes notes = new Notes(note,teacher.getCourse(),students);
        notesRepository.save(notes);
    }
}
