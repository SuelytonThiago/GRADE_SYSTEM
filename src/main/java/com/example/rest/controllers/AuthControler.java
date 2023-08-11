package com.example.rest.controllers;

import com.example.rest.dto.UserDto;
import com.example.rest.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthControler {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "perform user authentication with email and password returning a Map with access and update tokens")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid UserDto userDto){
        Map<String,String> map = authService.generateTokens(userDto);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/refresh")
    @Operation(summary = "updates access token via refresh token")
    public ResponseEntity<String> refresh(HttpServletRequest request){
       return ResponseEntity.ok(authService.attAccessToken(request));
    }


}
