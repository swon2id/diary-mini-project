package com.kh.mini_project.Common;

public class MemberQuery {
    public static final String INSERT_QUERY = "" +
            "INSERT INTO MEMBER(MEMBER_NUM, ID, PASSWORD, EMAIL, NICKNAME, REGISTRATION_DATE) " +
            "VALUES(SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?, ?)";
    public static final String SELECT_COUNT_BY_FIELD_AND_VALUE_QUERY = "SELECT COUNT(*) FROM MEMBER WHERE %s=?";
    public static final String SELECT_COUNT_BY_ID_AND_PASSWORD_QUERY = "SELECT COUNT(*) FROM MEMBER WHERE ID=? AND PASSWORD=?";
    public static final String SELECT_MEMBER_NUM_BY_ID_QUERY = "SELECT MEMBER_NUM FROM MEMBER WHERE ID=?";
}
