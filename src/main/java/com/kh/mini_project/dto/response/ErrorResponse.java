package com.kh.mini_project.dto.response;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorResponse {
    private boolean success = false;
    private String message;
    private Map<String, String> details;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, Map<String, String> details) {
        this.message = message;
        this.details = details;
    }
}
