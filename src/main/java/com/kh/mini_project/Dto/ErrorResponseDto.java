package com.kh.mini_project.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
    boolean success;
    String message;

    public ErrorResponseDto(String message) {
        this.success = false;
        this.message = message;
    }
}
