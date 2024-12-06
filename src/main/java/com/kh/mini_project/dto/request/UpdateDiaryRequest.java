package com.kh.mini_project.dto.request;

import com.kh.mini_project.dto.DiaryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDiaryRequest {
    @Valid
    @NotNull(message = "loggedInMember가 전달되지 않았습니다.")
    private AuthenticateLoginRequest loggedInMember;

    @NotBlank(message = "diaryNum이 전달되지 않았습니다.")
    private String diaryNum;

    @Valid
    @NotNull(message = "updatedDiary가 전달되지 않았습니다.")
    private DiaryDto updatedDiary;
}
