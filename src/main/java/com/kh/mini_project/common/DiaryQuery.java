package com.kh.mini_project.common;

public class DiaryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO DIARY(DIARY_NUM, MEMBER_NUM, TITLE, CONTENT, WRITTEN_DATE) " +
            "VALUES(SEQ_DIARY.NEXTVAL, ?, ?, ?, ?)";
    public static final String SELECT_BY_MEMBER_NUM_AND_DATE_QUERY = "" +
            "SELECT * FROM DIARY WHERE MEMBER_NUM = ? AND TO_CHAR(WRITTEN_DATE, 'YYYY') = ? AND TO_CHAR(WRITTEN_DATE, 'MM') = ?";
    public static final String SELECT_BY_DIARY_NUM_QUERY = "" +
            "SELECT * FROM DIARY WHERE DIARY_NUM = ?";
    public static final String UPDATE_QUERY = "UPDATE DIARY SET TITLE = ?, CONTENT = ?, WRITTEN_DATE = ? WHERE DIARY_NUM = ?";
    public static final String SELECT_DIARY_NUM_BY_MEMBER_NUM_QUERY = "SELECT DIARY_NUM FROM DIARY WHERE MEMBER_NUM = ?";
    public static final String DELETE_BY_DIARY_NUM_QUERY = "DELETE FROM DIARY WHERE DIARY_NUM = ?";
}
