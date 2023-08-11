package com.example.rest.service;

import com.example.domain.entities.Role;
import com.example.domain.entities.Users;
import com.example.domain.enums.Roles;
import com.example.domain.repositories.AdministratorRepository;
import com.example.domain.repositories.RoleRepository;
import com.example.domain.repositories.UserRepository;
import com.example.domain.entities.Administrator;
import com.example.rest.dto.AdministratorRequestDto;
import com.example.rest.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void insertNewAdmin(AdministratorRequestDto dto){

        Roles roles = Roles.nameOf("ADMIN");
        Role role = roleRepository.findByRoleName(roles).get();
        Administrator adm = new Administrator();
        adm.setName(dto.getName());
        adm.setCpf(dto.getCpf());
        adm.setEmail(dto.getEmail());
        adm.setPass(encoder.encode(dto.getPass()));
        administratorRepository.save(adm);
        adm.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return user;
    }
}
