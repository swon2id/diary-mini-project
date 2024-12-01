package com.kh.mini_project.Common;

public class MemberQuery {
    public static final String INSERT_MEMBER_QUERY = "" +
            "INSERT INTO MEMBER(MEMBER_NUM, ID, PASSWORD, EMAIL, NICKNAME, REGISTRATION_DATE) " +
            "VALUES(SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?, ?)";
    public static final String SELECT_MEMBER_COUNT_QUERY = "SELECT COUNT(*) FROM MEMBER WHERE %s=?";
}
