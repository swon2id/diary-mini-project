package com.kh.mini_project.common;

public class DiaryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO DIARY(DIARY_NUM, MEMBER_NUM, TITLE, CONTENT, WRITTEN_DATE) " +
            "VALUES(SEQ_DIARY.NEXTVAL, ?, ?, ?, ?)";
    public static final String SELECT_BY_ID_AND_DATE_QUERY = "" +
            "SELECT * FROM DIARY WHERE MEMBER_NUM = ? AND TO_CHAR(WRITTEN_DATE, 'YYYY') = ? AND TO_CHAR(WRITTEN_DATE, 'MM') = ?";
}
