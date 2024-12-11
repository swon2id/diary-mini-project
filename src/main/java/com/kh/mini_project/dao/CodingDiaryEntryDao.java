package com.kh.mini_project.dao;

import com.kh.mini_project.vo.CodingDiaryEntryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kh.mini_project.common.CodingDiaryEntryQuery.*;

@Repository
@RequiredArgsConstructor
public class CodingDiaryEntryDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(CodingDiaryEntryVo vo) {
        // INSERT 실패 경우의 수
        // 1. SQL 구문 오류 => DataAccessException
        // 2. 서브쿼리를 조건으로 사용했으나, 서브쿼리가 아무런 튜플을 반환하지 않은 경우 => update 메서드는 0을 반환

        // 서브 쿼리를 사용하지 않으므로, update 리턴 값 검증 로직 작성 X
        jdbcTemplate.update(INSERT_QUERY, vo.getCodingDiaryNum(), vo.getType(), vo.getProgrammingLanguageName(), vo.getContent(), vo.getSequence());
    }

    public List<CodingDiaryEntryVo> selectByCodingDiaryNum(int codingDiaryNum) {
        return jdbcTemplate.query(SELECT_BY_CODING_DIARY_NUM_QUERY, new Object[]{codingDiaryNum}, (rs, rowNum) -> new CodingDiaryEntryVo(
                rs.getInt("CODING_DIARY_ENTRY_NUM"),
                rs.getInt("CODING_DIARY_NUM"),
                rs.getString("TYPE"),
                rs.getString("PROGRAMMING_LANGUAGE_NAME"),
                rs.getString("CONTENT"),
                rs.getInt("SEQUENCE")
        ));
    }

    public void deleteByCodingDiaryNum(int codingDiaryNum) {
        jdbcTemplate.update(DELETE_BY_CODING_DIARY_NUM_QUERY, codingDiaryNum);
    }
}
