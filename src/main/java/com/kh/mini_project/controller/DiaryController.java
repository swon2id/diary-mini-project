package com.kh.mini_project.controller;

import com.kh.mini_project.dto.DiarySaveRequestDto;
import com.kh.mini_project.service.AuthService;
import com.kh.mini_project.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    private final AuthService authService;
    private final DiaryService diaryService;

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> addDiary(@Valid @RequestBody DiarySaveRequestDto dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            diaryService.saveNewDiary(dto.getLoggedInMember(), dto.getNewDiary());
            response.put("success", true);
            httpStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
