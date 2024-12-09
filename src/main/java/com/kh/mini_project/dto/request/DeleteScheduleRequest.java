package com.kh.mini_project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteScheduleRequest {
    @Valid
    @NotNull(message = "loggedInMember가 전달되지 않았습니다.")
    private AuthenticateLoginRequest loggedInMember;

    @NotBlank(message = "scheduleNum이 전달되지 않았습니다.")
    private String scheduleNum;
}
