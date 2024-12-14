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
        log.info("Received update request for diary setting: {}", dto);
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!dto.getUpdatedDiarySetting().isValid()) {
            response.put("success", false);
            response.put("message", "전달된 필드가 존재하지 않습니다.");
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        else if (!authService.validateCredentials(dto.getLoggedInMember())) {
            log.warn("Authentication failed for user: {}", dto.getLoggedInMember().getId());
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            try {
                log.info("User authenticated, proceeding to update settings: {}", dto.getUpdatedDiarySetting());
                diarySettingService.updateDiarySetting(dto.getLoggedInMember(), dto.getUpdatedDiarySetting());
                response.put("success", true);
                response.put("isUpdated", true);
                httpStatus = HttpStatus.OK;
            } catch (Exception e) {
                log.error("Error occurred while updating diary settings for user: " + dto.getLoggedInMember().getId(), e);
                response.put("success", false);
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
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
