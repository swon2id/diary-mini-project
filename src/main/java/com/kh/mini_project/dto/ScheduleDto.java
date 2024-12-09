package com.kh.mini_project.dto;

import com.kh.mini_project.validation.AllDayDateCheck;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.LinkedHashSet;

@Data
@AllDayDateCheck
public class ScheduleDto {
    @NotBlank(message = "일정의 title이 전달되지 않았습니다.")
    private String title;

    private String description;

    @NotNull(message = "일정의 startDate가 전달되지 않았습니다.")
    // ISO 8601 시간 표기에 대한 표준 형식
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "일정의 startDate는 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다."
    )
    private String startDate;

    @NotNull(message = "일정의 endDate가 전달되지 않았습니다.")
    // ISO 8601 시간 표기에 대한 표준 형식
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "일정의 endDate는 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다."
    )
    private String endDate;

    @NotNull(message = "일정의 isAllday가 전달되지 않았습니다.")
    private Boolean isAllday;

    @NotNull(message = "일정의 isImportant 전달되지 않았습니다.")
    private Boolean isImportant;

    // 값이 전달되지 않을 수 있음
    // 중복 불허, 순서 보장
    @Valid
    private LinkedHashSet<NotificationDto> notifications;

    @Data
    public static class NotificationDto {
        @NotNull(message = "알림의 alertTime가 전달되지 않았습니다.")
        // ISO 8601 시간 표기에 대한 표준 형식
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
                message = "알림의 alertTime은 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다."
        )
        private String alertTime;

        @NotBlank(message = "알림의 alertMethod가 전달되지 않았습니다.")
        private String alertMethod;
    }
}
