package com.kh.mini_project.dto.response;

import com.kh.mini_project.vo.SettingVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SettingsResponse {
    private int diarySettingNum;
    private int memberNum;
    private String currentTheme;
    private String currentFont;
    private String currentMainBannerImage;
    private String currentAlertSound;

    public static SettingsResponse from(SettingVo settingVo) {
        return new SettingsResponse(
                settingVo.getDiarySettingNum(),
                settingVo.getMemberNum(),
                settingVo.getCurrentTheme() != null ? settingVo.getCurrentTheme() : "default",
                settingVo.getCurrentFont() != null ? settingVo.getCurrentFont() : "default",
                settingVo.getCurrentMainBannerImage() != null ? settingVo.getCurrentMainBannerImage() : "default.jpg",
                settingVo.getCurrentAlertSound() != null ? settingVo.getCurrentAlertSound() : "default"
        );
    }
}
