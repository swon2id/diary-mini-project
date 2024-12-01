package com.kh.mini_project.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDto {
    @NotBlank(message = "ID가 전달되지 않았습니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9_-]+$",
        message = "ID는 영문자, 숫자, 언더스코어(_) 또는 하이픈(-)만 포함해야 합니다."
    )
    @Size(max = 30, message = "ID는 30글자 이하여야 합니다.")
    private String id;

    @NotBlank(message = "Password가 전달되지 않았습니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9!@#$%^&*()]+$",
        message = "Password는 영문자, 숫자 및 !@#$%^&*() 특수문자만 포함해야 합니다."
    )
    @Size(min = 8, max = 30, message = "Password는 8글자 이상, 30글자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "Email이 전달되지 않았습니다.")
    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
        message = "Email은 올바른 이메일 형식을 따라야 합니다."
    )
    @Size(max = 30, message = "Email은 30글자 이하여야 합니다.")
    private String email;

    @NotBlank(message = "Nickname이 전달되지 않았습니다.")
    @Size(max = 20, message = "Nickname은 20글자 이하여야 합니다.")
    private String nickname;
}