package com.kh.mini_project.controller;

import com.kh.mini_project.dto.MonthlyDiaryEntryDto;
import com.kh.mini_project.dto.request.GetMonthlyDiaryListRequest;
import com.kh.mini_project.dto.request.SaveNewDiaryRequest;
import com.kh.mini_project.service.AuthService;
import com.kh.mini_project.service.DiaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    private final AuthService authService;
    private final DiaryService diaryService;

    @PostMapping("save")
    public ResponseEntity<Map<String, Object>> handleSaveNewDiary(@Valid @RequestBody SaveNewDiaryRequest dto) {
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

    @PostMapping("get-monthly-list")
    public ResponseEntity<Map<String, Object>> handleGetMonthlyDiaryList(@Valid @RequestBody GetMonthlyDiaryListRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            List<MonthlyDiaryEntryDto> diaries = diaryService.getDiaryListMonthly(dto.getLoggedInMember(), dto.getDate());
            response.put("success", true);
            response.put("diaries", diaries);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
