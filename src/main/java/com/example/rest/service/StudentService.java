package com.example.rest.service;

import com.example.config.security.JWTService;
import com.example.domain.entities.Notes;
import com.example.domain.entities.Role;
import com.example.domain.entities.Students;
import com.example.domain.enums.Roles;
import com.example.domain.repositories.NotesRepository;
import com.example.domain.repositories.RoleRepository;
import com.example.domain.repositories.StudentRepository;
import com.example.rest.dto.NotesResponseDto;
import com.example.rest.dto.StudentRequestDto;
import com.example.rest.dto.StudentResponseDto;
import com.example.rest.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private NotesRepository notesRepository;

    @Transactional
    public void insert(StudentRequestDto studentsDto){

        Roles roles = Roles.nameOf("STUDENT");
        Role role = roleRepository.findByRoleName(roles).get();
        Students student = new Students(null,
                studentsDto.getName(),
                studentsDto.getCpf(),
                studentsDto.getEmail(),
                encoder.encode(studentsDto.getPass()));
        studentRepository.save(student);
        student.getRoles().add(role);
    }

    public List<StudentResponseDto> findAll(){
        return studentRepository.findAll().stream().map(student ->{
            StudentResponseDto studentDto = new StudentResponseDto();
            studentDto.setName(student.getName());
            return studentDto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<NotesResponseDto> findAllStudentsNotes(HttpServletRequest request){
        var auth = request.getHeader("Authorization");
        var token = auth.replace("Bearer ","");
        var email = jwtService.getSubject(token);
        var student = studentRepository.findByEmail(email).get();

        List<Notes> notes = notesRepository.findByStudents(student);
        List<NotesResponseDto> notesDto = convertListNotes(notes);
        return notesDto;
    }

    private List<NotesResponseDto> convertListNotes(List<Notes> notes){
        return notes.stream().map(note ->
                NotesResponseDto.builder()
                        .note(note.getNote())
                        .courseName(note.getCourse().getName())
                        .build()).collect(Collectors.toList());
    }
}
