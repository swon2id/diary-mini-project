package com.kh.mini_project.common;

public class CodingDiaryEntryQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO CODING_DIARY_ENTRY(CODING_DIARY_ENTRY_NUM, CODING_DIARY_NUM, TYPE, PROGRAMMING_LANGUAGE_NAME, CONTENT, SEQUENCE) " +
            "VALUES(SEQ_CODING_DIARY_ENTRY.NEXTVAL, ?, ?, ?, ?, ?)";
    public static final String SELECT_BY_CODING_DIARY_NUM_QUERY = "" +
            "SELECT * FROM CODING_DIARY_ENTRY WHERE CODING_DIARY_NUM = ?";
    public static final String DELETE_BY_CODING_DIARY_NUM_QUERY = "DELETE FROM CODING_DIARY_ENTRY WHERE CODING_DIARY_NUM = ?";
}
