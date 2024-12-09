package com.kh.mini_project.validation;

import com.kh.mini_project.dto.ScheduleDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class AllDayDateValidator implements ConstraintValidator<AllDayDateCheck, ScheduleDto> {

    // 시작 시간이 yyyy-MM-ddTHH:00:00 형식인지 검증하는 정규식
    private static final Pattern START_TIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T00:00:00$");
    // 종료 시간이 yyyy-MM-ddTHH:23:59 형식인지 검증하는 정규식
    private static final Pattern END_TIME_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T23:59:59$");

    @Override
    public boolean isValid(ScheduleDto dto, ConstraintValidatorContext context) {
        // isAllday가 false면 별도 검증 불필요
        if (dto.getIsAllday() == null || !dto.getIsAllday()) {
            return true;
        }

        // isAllday = true인 경우 startDate, endDate 모두 검사
        String start = dto.getStartDate();
        String end = dto.getEndDate();

        boolean startValid = start != null && START_TIME_PATTERN.matcher(start).matches();
        boolean endValid = end != null && END_TIME_PATTERN.matcher(end).matches();

        if (!startValid || !endValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("All day 일정인 경우 startDate는 'yyyy-MM-ddT00:00:00', endDate는 'yyyy-MM-ddT23:59:59' 이어야 합니다.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
