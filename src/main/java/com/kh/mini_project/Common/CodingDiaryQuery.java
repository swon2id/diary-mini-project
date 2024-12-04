package com.kh.mini_project.Common;

public class CodingDiaryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO CODING_DIARY(CODING_DIARY_NUM, DIARY_NUM) " +
            "VALUES(SEQ_CODING_DIARY.NEXTVAL, ?)";
}
