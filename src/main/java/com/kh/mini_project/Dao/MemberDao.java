package com.kh.mini_project.Dao;

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
    private static final String SELECT_MEMBER_COUNT_QUERY = "SELECT COUNT(*) FROM MEMBER WHERE %s=?";

    public void insert(MemberVo vo){
        int res = jdbcTemplate.update(INSERT_QUERY, vo.getId(), vo.getPassword(), vo.getEmail(), vo.getNickname(), vo.getRegistrationDate());
        if (res != 1) {
            String errorMessage = String.format("SQL: %s, 파라미터: [id: %s, password: %s, email: %s, nickname: %s, registrationDate: %s]",
                    INSERT_QUERY, vo.getId(), vo.getPassword(), vo.getEmail(), vo.getNickname(), vo.getRegistrationDate());
            throw new InsertFailureException(errorMessage);
        }
    }

    public int selectCount(String field, String value) {
        String sql = String.format(SELECT_MEMBER_COUNT_QUERY, field.toUpperCase());
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{value}, Integer.class);
        System.out.println(count);
        return count == null ? 0 : count;
    }
}
