package com.kh.mini_project.common;

public class SettingQuery {
    public static final String SELECT_DIARY_SETTING = "" +
            "SELECT DIARY_SETTING_NUM, MEMBER_NUM, CURRENT_THEME, CURRENT_FONT, CURRENT_MAIN_BANNER_IMAGE, CURRENT_ALERT_SOUND " +
            "FROM DIARY_SETTING WHERE MEMBER_NUM = ?";

    public static final String UPDATE_DIARY_SETTING = "" +
            "UPDATE DIARY_SETTING " +
            "SET CURRENT_THEME = ?, CURRENT_FONT = ?, CURRENT_MAIN_BANNER_IMAGE = ?, CURRENT_ALERT_SOUND = ? " +
            "WHERE MEMBER_NUM = ?";
}
