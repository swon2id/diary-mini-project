package com.kh.mini_project.common;

public class CodingDiaryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO CODING_DIARY(CODING_DIARY_NUM, DIARY_NUM) " +
            "VALUES(SEQ_CODING_DIARY.NEXTVAL, ?)";
    public static final String SELECT_BY_DIARY_NUM = "" +
            "SELECT * FROM CODING_DIARY WHERE DIARY_NUM = ?";
}
