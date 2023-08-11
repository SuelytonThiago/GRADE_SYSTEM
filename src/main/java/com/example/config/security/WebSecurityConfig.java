package com.example.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private JWTFilter filter;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST,"/api/auth/login").permitAll()

                .requestMatchers(
                        "/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                ).permitAll()

                .requestMatchers(HttpMethod.GET,"/api/courses").hasAnyRole("ADMIN","TEACHER")
                .requestMatchers(HttpMethod.GET,"/api/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/courses").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/courses/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/courses/addStudentToCourse").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/courses/removeStudentToCourse").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/api/schoolcards/addNote").hasAnyRole("ADMIN","TEACHER")

                .requestMatchers(HttpMethod.POST,"/api/students").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/students/getnotes/**").hasAnyRole("ADMIN","STUDENT","TEACHER")
                .requestMatchers(HttpMethod.GET,"/api/students/getallstudents").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/api/teachers/addteacher").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/teachers").hasAnyRole("ADMIN","TEACHER")
                .requestMatchers(HttpMethod.PUT,"/api/teachers/updatestudentnote").hasRole("TEACHER")
                .requestMatchers(HttpMethod.POST,"/api/teachers/addstudentnote").hasRole("TEACHER")

                .requestMatchers(HttpMethod.POST,"/api/users/addRole/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/users/addAdm").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST,"/api/roles/addRole").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/roles/getRole").hasRole("ADMIN")
                .anyRequest().authenticated().and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
