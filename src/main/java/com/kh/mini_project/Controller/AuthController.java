package com.kh.mini_project.Controller;

import com.kh.mini_project.Dto.RegisterRequestDto;
import com.kh.mini_project.Service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Boolean>> registerMember(@RequestBody RegisterRequestDto dto) {
        log.info("URL : \"/auth/register\", 전달된 데이터: {}", dto);
        authService.registerMember(dto);

        Map<String, Boolean> response = new HashMap<>();
        response.put("isSuccess", true);
        return ResponseEntity.ok(response);
    }
}
