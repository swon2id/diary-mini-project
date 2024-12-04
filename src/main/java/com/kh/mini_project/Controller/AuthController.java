package com.kh.mini_project.Controller;

import com.kh.mini_project.Dto.LoginAuthenticationRequestDto;
import com.kh.mini_project.Dto.SignUpCheckUniqueRequestDto;
import com.kh.mini_project.Dto.SignUpRequestDto;
import com.kh.mini_project.Service.AuthService;
import com.kh.mini_project.Service.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Map<String, Object>> signup(@Valid @RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/signup/check-unique")
    public ResponseEntity<Map<String, Object>> checkDuplicate(@Valid SignUpCheckUniqueRequestDto dto) {
        boolean isUnique = validationService.isUnique(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginAuthenticationRequestDto dto) {
        boolean isAuthenticated = authService.validateCredentials(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("isAuthenticated", isAuthenticated);
        return ResponseEntity.ok(response);
    }
}
