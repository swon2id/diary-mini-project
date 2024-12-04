package com.kh.mini_project.dao;

import com.kh.mini_project.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// import static 이란?
// 클래스명을 생략하고 static 멤버 접근 가능
import static com.kh.mini_project.common.MemberQuery.*;


@Repository
@RequiredArgsConstructor
public class MemberDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(MemberVo vo) {
        // INSERT 실패 경우의 수
        // 1. SQL 구문 오류 => DataAccessException
        // 2. 서브쿼리를 조건으로 사용했으나, 서브쿼리가 아무런 튜플을 반환하지 않은 경우 => update 메서드는 0을 반환

        // 서브 쿼리를 사용하지 않으므로, update 리턴 값 검증 로직 작성 X
        jdbcTemplate.update(INSERT_QUERY, vo.getId(), vo.getPassword(), vo.getEmail(), vo.getNickname(), vo.getRegistrationDate());
    }

    public int selectCountByFieldAndValue(String field, String value) {
        Integer count = jdbcTemplate.queryForObject(String.format(SELECT_COUNT_BY_FIELD_AND_VALUE_QUERY, field.toUpperCase()), new Object[]{value}, Integer.class);
        return count == null ? 0 : count;
    }

    public int selectCountByIdAndPassword(String id, String password) {
        Integer count = jdbcTemplate.queryForObject(SELECT_COUNT_BY_ID_AND_PASSWORD_QUERY, new Object[]{id, password}, Integer.class);
        return count == null ? 0 : count;
    }

    public int selectMemberNumById(String id) {
        Integer memberNum = jdbcTemplate.queryForObject(SELECT_MEMBER_NUM_BY_ID_QUERY, new Object[]{id}, Integer.class);
        return memberNum == null ? -1 : memberNum;
    }
}
