package com.kh.mini_project.controller;

import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.dto.request.CheckUniqueStatusRequest;
import com.kh.mini_project.dto.request.SignUpRequest;
import com.kh.mini_project.service.AuthService;
import com.kh.mini_project.service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final ValidationService validationService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody SignUpRequest dto) {
        authService.signUp(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/signup/check-unique")
    public ResponseEntity<Map<String, Object>> checkDuplicate(@Valid CheckUniqueStatusRequest dto) {
        boolean isUnique = validationService.isUnique(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("isUnique", isUnique);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthenticateLoginRequest dto) {
        boolean isAuthenticated = authService.validateCredentials(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("isAuthenticated", isAuthenticated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
