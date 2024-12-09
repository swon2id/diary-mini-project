package com.kh.mini_project.controller;

import com.kh.mini_project.dto.request.*;
import com.kh.mini_project.service.AuthService;
import com.kh.mini_project.service.ScheduleService;
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
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final AuthService authService;
    private final ScheduleService scheduleService;

    @PostMapping("save")
    public ResponseEntity<Map<String, Object>> handleSaveNewSchedule(@Valid @RequestBody SaveNewScheduleRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            scheduleService.saveNewSchedule(dto.getLoggedInMember(), dto.getNewSchedule());
            response.put("success", true);
            httpStatus = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("get-monthly-list")
    public ResponseEntity<Map<String, Object>> handleGetMonthlyScheduleList(@Valid @RequestBody GetMonthlyDataListRequest dto) {
        // pass
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("get")
    public ResponseEntity<Map<String, Object>> handleGetSchedule(@Valid @RequestBody GetOrDeleteDiaryRequest dto) {
        // pass
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<Map<String, Object>> handleUpdateSchedule(@Valid @RequestBody UpdateScheduleRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            scheduleService.updateSchedule(dto.getLoggedInMember(), Integer.parseInt(dto.getScheduleNum()), dto.getUpdatedSchedule());
            response.put("success", true);
            response.put("isUpdated", true);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("delete")
    public ResponseEntity<Map<String, Object>> handleDeleteSchedule(@Valid @RequestBody GetOrDeleteScheduleRequest dto) {
        Map<String, Object> response = new HashMap<>();
        HttpStatus httpStatus;

        if (!authService.validateCredentials(dto.getLoggedInMember())) {
            response.put("success", false);
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else {
            scheduleService.deleteSchedule(dto.getLoggedInMember(), Integer.parseInt(dto.getScheduleNum()));
            response.put("success", true);
            response.put("isDeleted", true);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
