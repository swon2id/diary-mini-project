package com.kh.mini_project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    private boolean success = false;
    private String message;
    private Map<String, String> details;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public ErrorResponseDto(String message, Map<String, String> details) {
        this.message = message;
        this.details = details;
    }
}
