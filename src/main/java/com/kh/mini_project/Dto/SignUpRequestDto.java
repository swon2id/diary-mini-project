package com.kh.mini_project.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @NotBlank(message = "id가 전달되지 않았습니다.")
    private String id;

    @NotBlank(message = "password가 전달되지 않았습니다.")
    private String password;

    @NotBlank(message = "email이 전달되지 않았습니다.")
    private String email;

    @NotBlank(message = "nickname이 전달되지 않았습니다.")
    private String nickname;
}