package com.example.config;

import com.example.domain.entities.*;
import com.example.domain.repositories.*;
import com.example.domain.enums.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class Config implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Administrator administrator =
                new Administrator(
                        null,
                        "adm",
                        "48691074060",
                        "adm@example.com",
                        encoder.encode("senha123"));
        administratorRepository.save(administrator);
        Students students =
                new Students(
                        "ana",
                        "26948568025",
                        "ana@example.com",
                        encoder.encode("senha123")
                );
        studentRepository.save(students);

        Course course = new Course("matematic");
        courseRepository.save(course);

        Teacher teacher = new Teacher(
                "bob",
                "26948568025",
                "bob@example.com",
                encoder.encode("senha123") ,
                course
        );
        teacherRepository.save(teacher);

        Role role = new Role(null, Roles.ROLE_ADMIN);
        Role role1 = new Role(null, Roles.ROLE_TEACHER);
        Role role2 = new Role(null, Roles.ROLE_STUDENT);
        roleRepository.saveAll(Arrays.asList(role,role1,role2));

        students.getRoles().add(role2);
        teacher.getRoles().add(role1);
        administrator.getRoles().add(role);
        administratorRepository.save(administrator);
        teacherRepository.save(teacher);
        studentRepository.save(students);
    }
}
