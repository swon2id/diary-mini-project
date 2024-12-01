package com.kh.mini_project.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpCheckUniqueRequestDto {
    @NotBlank(message = "Field가 전달되지 않았습니다.")
    @Pattern(regexp = "^(id|email|nickname)$", message = "field는 'id', 'email', 'nickname' 중 하나여야 합니다.")
    String field;

    @NotBlank(message = "Value가 전달되지 않았습니다.")
    String value;
}
