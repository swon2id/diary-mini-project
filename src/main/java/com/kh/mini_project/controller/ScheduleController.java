package com.kh.mini_project.controller;

import com.kh.mini_project.dto.request.SaveNewScheduleRequest;
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
}
