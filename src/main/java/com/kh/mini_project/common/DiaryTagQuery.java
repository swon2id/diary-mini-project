package com.kh.mini_project.common;

public class DiaryTagQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO DIARY_TAG(DIARY_TAG_NUM, DIARY_NUM, TAG_NAME) " +
            "VALUES(SEQ_DIARY_TAG.NEXTVAL, ?, ?)";
}
