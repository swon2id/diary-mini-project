package com.kh.mini_project.Dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String id;
    private String password;
    private String email;
    private String nickname;
}