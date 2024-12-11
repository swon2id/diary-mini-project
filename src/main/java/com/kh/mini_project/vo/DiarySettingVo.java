package com.kh.mini_project.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiarySettingVo {
    private Integer diarySettingNum;
    private Integer memberNum;
    private String currentTheme;
    private String currentFont;
    private String currentMainBannerImage;
    private String currentAlertSound;
}
