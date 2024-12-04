package com.kh.mini_project.Common;

public class ProgrammingLanguageQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO PROGRAMMING_LANGUAGE(PROGRAMMING_LANGUAGE_NUM, NAME) " +
            "VALUES(SEQ_PROGRAMMING_LANGUAGE.NEXTVAL, ?)";
}
