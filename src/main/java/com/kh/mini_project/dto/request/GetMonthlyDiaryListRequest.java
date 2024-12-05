package com.kh.mini_project.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class GetMonthlyDiaryListRequest {
    @Valid
    private AuthenticateLoginRequest loggedInMember;

    @Valid
    private DateDto date;

    @Data
    public static class DateDto {
        @NotBlank(message = "연도(year)가 전달되지 않았습니다.")
        @Pattern(
                regexp = "^\\d{4}$",
                message = "연도(year)는 4자리 정수여야 합니다."
        )
        private String year;

        @NotBlank(message = "월(month)이 전달되지 않았습니다.")
        @Pattern(
                regexp = "^(1[0-2]|[1-9])$",
                message = "월(month)은 1~12 사이의 정수여야 합니다."
        )
        private String month;
    }
}
