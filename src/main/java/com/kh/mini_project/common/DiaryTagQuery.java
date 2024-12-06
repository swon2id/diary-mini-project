package com.kh.mini_project.common;

public class DiaryTagQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO DIARY_TAG(DIARY_TAG_NUM, DIARY_NUM, TAG_NAME) " +
            "VALUES(SEQ_DIARY_TAG.NEXTVAL, ?, ?)";
    public static final String SELECT_BY_DIARY_NUM_QUERY = "" +
            "SELECT * FROM DIARY_TAG WHERE DIARY_NUM = ?";
    public static final String DELETE_BY_DIARY_NUM_QUERY = "DELETE FROM DIARY_TAG WHERE DIARY_NUM = ?";
}
