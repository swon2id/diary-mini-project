package com.kh.mini_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DiarySettingDto {
    @Pattern(regexp = "^(Do Hyeon|Gowun Dodum|Hi Melody|Jua|default)$", message = "font는 'Do Hyeon', 'Gowun Dodum', 'Hi Melody', 'Jua', 'default' 중 하나여야 합니다.")
    private String font;

    @Pattern(regexp = "^(dark|default)$", message = "theme는 'dark', 'default' 중 하나여야 합니다.")
    private String theme;

    @Pattern(regexp = "^(banner1|banner2|banner3|banner4|default)$", message = "mainBannerImage는 'banner1~4', 'default' 중 하나여야 합니다.")
    private String mainBannerImage;
    private String alertSound;

    /**
     * 모든 필드 중 적어도 하나가 비어 있지 않은지 확인합니다.
     *
     * @return boolean
     */
    public boolean isValid() {
        return font != null && !font.isBlank() ||
                theme != null && !theme.isBlank() ||
                mainBannerImage != null && !mainBannerImage.isBlank() ||
                alertSound != null && !alertSound.isBlank();
    }
}
