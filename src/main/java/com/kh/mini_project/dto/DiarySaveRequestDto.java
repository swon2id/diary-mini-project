package com.kh.mini_project.dto;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public class DiarySaveRequestDto {
    @Valid
    private LoginAuthenticationRequestDto loggedInMember;

    @Valid
    private DiaryDto newDiary;
}
