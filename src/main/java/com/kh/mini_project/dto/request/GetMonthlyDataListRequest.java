package com.kh.mini_project.dto.request;

import com.kh.mini_project.dto.MonthlyDateDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetMonthlyDataListRequest {
    @Valid
    @NotNull(message = "loggedInMember가 전달되지 않았습니다.")
    private AuthenticateLoginRequest loggedInMember;

    @Valid
    @NotNull(message = "date가 전달되지 않았습니다.")
    private MonthlyDateDto date;
}
