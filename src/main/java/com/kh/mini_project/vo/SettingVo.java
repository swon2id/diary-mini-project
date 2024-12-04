package com.kh.mini_project.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingVo {
    private int diarySettingNum;
    private int memberNum;
    private String currentTheme;
    private String currentFont;
    private String currentMainBannerImage;
    private String currentAlertSound;
}
