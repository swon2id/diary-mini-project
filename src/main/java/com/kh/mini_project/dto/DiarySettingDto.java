package com.kh.mini_project.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DiarySettingDto {
    @NotBlank(message = "폰트가 전달되지 않았습니다.")
    private String font;

    @NotBlank(message = "테마가 전달되지 않았습니다.")
    private String theme;

    @NotBlank(message = "메인 배너 이미지가 전달되지 않았습니다.")
    private String mainBannerImage;

    @NotBlank(message = "알림 소리가 전달되지 않았습니다.")
    private String alertSound;
}
