package com.kh.mini_project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetOrDeleteDiaryRequest {
    @Valid
    @NotNull(message = "loggedInMember가 전달되지 않았습니다.")
    private AuthenticateLoginRequest loggedInMember;

    @NotBlank(message = "DiaryNum이 전달되지 않았습니다.")
    private String diaryNum;
}
