package com.kh.mini_project.dto;

import lombok.Data;

import java.util.Map;

@Data
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
