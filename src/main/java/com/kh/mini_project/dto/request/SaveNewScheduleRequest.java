package com.kh.mini_project.dto.request;

import com.kh.mini_project.dto.ScheduleDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaveNewScheduleRequest {
    @Valid
    @NotNull(message = "loggedInMember가 전달되지 않았습니다.")
    private AuthenticateLoginRequest loggedInMember;

    @Valid
    @NotNull(message = "schedule 정보가 전달되지 않았습니다.")
    private ScheduleDto newSchedule;
}
