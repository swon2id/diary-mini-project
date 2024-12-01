package com.kh.mini_project.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "ID가 전달되지 않았습니다.")
    String id;

    @NotBlank(message = "Password가 전달되지 않았습니다.")
    String password;
}
