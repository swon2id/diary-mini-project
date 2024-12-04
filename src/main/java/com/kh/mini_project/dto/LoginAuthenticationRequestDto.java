package com.kh.mini_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginAuthenticationRequestDto {
    @NotBlank(message = "ID가 전달되지 않았습니다.")
    private String id;

    @NotBlank(message = "Password가 전달되지 않았습니다.")
    private String password;
}
