package com.kh.mini_project.common;

public class DiaryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO DIARY(DIARY_NUM, MEMBER_NUM, TITLE, CONTENT, WRITTEN_DATE) " +
            "VALUES(SEQ_DIARY.NEXTVAL, ?, ?, ?, ?)";
}
