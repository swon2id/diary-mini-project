package com.kh.mini_project.dto;

import com.kh.mini_project.validation.ValidSequentialSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.LinkedHashSet;
import java.util.List;

@Data
public class DiaryDto {
    @NotBlank(message = "일기 제목이 전달되지 않았습니다.")
    private String title;

    @NotBlank(message = "일기 내용이 전달되지 않았습니다.")
    private String content;

    // 값이 전달되지 않을 수 있음
    // 중복 불허, 순서 보장
    private LinkedHashSet<String> tags;

    @NotNull(message = "작성일이 전달되지 않았습니다.")
    // ISO 8601 시간 표기에 대한 표준 형식
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "작성일은 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다."
    )
    private String writtenDate;

    @Valid
    @ValidSequentialSequence
    private List<CodingDiaryEntryDto> codingDiaryEntries;

    @Data
    public static class CodingDiaryEntryDto {
        @NotBlank(message = "코딩일기항목의 entryType이 전달되지 않았습니다.")
        @Pattern(regexp = "^(snippet|comment)$", message = "entryType은 'snippet', 'comment' 중 하나여야 합니다.")
        private String entryType;

        // 값이 전달되지 않을 수 있음 -> entryType에 따라 검증?
        private String programmingLanguageName;

        @NotBlank(message = "코딩일기항목의 내용이 전달되지 않았습니다.")
        private String content;

        @NotNull(message = "코딩일기항목의 순서가 전달되지 않았습니다.")
        private Integer sequence;
    }
}
