package com.kh.mini_project.Dao;

import com.kh.mini_project.Common.TimeUtils;
import com.kh.mini_project.Vo.MemberVo;
import com.kh.mini_project.exception.InsertFailureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = "" +
            "INSERT INTO MEMBER(MEMBER_NUM, ID, PASSWORD, EMAIL, NICKNAME, REGISTRATION_DATE) " +
            "VALUES(SEQ_MEMBER.NEXTVAL, ?, ?, ?, ?, ?)";

    public void insert(MemberVo vo){
        int res =  jdbcTemplate.update(INSERT_QUERY, vo.getId(), vo.getPassword(), vo.getEmail(), vo.getNickname(), TimeUtils.getCurrentTimestamp());
        if (res != 1) {
            throw new InsertFailureException("회원 데이터 INSERT 실패");
        }
    }
}
