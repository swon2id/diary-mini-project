package com.kh.mini_project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetDiaryRequest {
    @Valid
    private AuthenticateLoginRequest loggedInMember;

    @NotBlank(message = "DiaryNum이 전달되지 않았습니다.")
    private String diaryNum;
}
