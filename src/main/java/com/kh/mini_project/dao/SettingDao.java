package com.kh.mini_project.dao;

import com.kh.mini_project.vo.SettingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.common.SettingQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SettingDao {
    private final JdbcTemplate jdbcTemplate;

    public SettingVo getDiarySetting(int memberNum) {
        return jdbcTemplate.queryForObject(
                SELECT_DIARY_SETTING,
                new Object[]{memberNum},
                (rs, rowNum) -> new SettingVo(
                        rs.getInt("DIARY_SETTING_NUM"),
                        rs.getInt("MEMBER_NUM"),
                        rs.getString("CURRENT_THEME"),
                        rs.getString("CURRENT_FONT"),
                        rs.getString("CURRENT_MAIN_BANNER_IMAGE"),
                        rs.getString("CURRENT_ALERT_SOUND")
                )
        );
    }

    public void updateSetting(SettingVo settingVo) {
        jdbcTemplate.update(
                UPDATE_DIARY_SETTING,
                settingVo.getCurrentTheme(),
                settingVo.getCurrentFont(),
                settingVo.getCurrentMainBannerImage(),
                settingVo.getCurrentAlertSound(),
                settingVo.getMemberNum()
        );
    }
}
