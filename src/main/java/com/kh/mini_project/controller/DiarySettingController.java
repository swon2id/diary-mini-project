package com.kh.mini_project.controller;

import com.kh.mini_project.dto.DiarySettingDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.dto.request.UpdateDiarySettingRequest;
import com.kh.mini_project.service.AuthService;
import com.kh.mini_project.service.DiarySettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/setting")
public class DiarySettingController {
    private final AuthService authService;
    private final DiarySettingService diarySettingService;

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> handleUpdateDiarySetting(@Valid @RequestBody UpdateDiarySettingRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            diarySettingService.updateDiarySetting(dto.getLoggedInMember(), dto.getUpdatedDiarySetting());
            response.put("success", true);
            response.put("isUpdated", true);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("/get")
    public ResponseEntity<Map<String, Object>> handleGetDiarySetting(@Valid @RequestBody AuthenticateLoginRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto)) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            DiarySettingDto diarySetting = diarySettingService.getDiarySetting(dto);
            response.put("success", true);
            response.put("diarySetting", diarySetting);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
