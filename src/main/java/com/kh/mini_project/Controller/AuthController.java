package com.kh.mini_project.Controller;

import com.kh.mini_project.Dto.SignUpRequestDto;
import com.kh.mini_project.Service.AuthService;
import com.kh.mini_project.Service.ValidationService;
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
    private final ValidationService validationService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignUpRequestDto dto) {
        log.info("URL : \"/auth/signup\", 전달된 데이터: {}", dto);
        authService.signUp(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/signup/check-unique")
    public ResponseEntity<Map<String, Object>> checkDuplicate(@RequestParam String field, @RequestParam String value) {
        log.info("URL : \"/auth/signup/check-unique\", 전달된 데이터: field:{}, value:{}", field, value);
        boolean isUnique = validationService.isUnique(field, value);

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", true);
        response.put("isUnique", isUnique);
        return ResponseEntity.ok(response);
    }
}
