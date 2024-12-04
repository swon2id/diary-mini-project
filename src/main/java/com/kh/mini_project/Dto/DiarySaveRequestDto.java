package com.kh.mini_project.Dto;

import com.kh.mini_project.Validation.ValidSequentialSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.util.List;

@Data
public class DiarySaveRequestDto {
    @Valid
    private LoginAuthenticationRequestDto loggedInMember;

    @Valid
    private DiaryDto newDiary;

    @Data
    public static class DiaryDto {
        @NotBlank(message = "일기 제목이 전달되지 않았습니다.")
        private String title;

        @NotBlank(message = "일기 내용이 전달되지 않았습니다.")
        private String content;

        // 값이 전달되지 않을 수 있음
        private List<String> tags;

        @NotNull(message = "작성일이 전달되지 않았습니다.")
        // ISO 8601 시간 표기에 대한 표준 형식
        @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$",
            message = "작성일은 'yyyy-MM-ddTHH:mm:ss' 형식이어야 합니다."
        )
        private String writtenDate;

        @ValidSequentialSequence
        private List<@Valid CodingDiaryEntryDto> codingDiaryEntries;

        @Data
        public static class CodingDiaryEntryDto {
            // 값이 전달되지 않을 수 있음
            private String programmingLanguageName;

            @NotBlank(message = "코딩일기항목의 내용이 전달되지 않았습니다.")
            private String content;

            @NotNull(message = "코딩일기항목의 순서가 전달되지 않았습니다.")
            private Integer sequence;
        }
    }
}
