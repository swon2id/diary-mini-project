package com.kh.mini_project.dao;

import com.kh.mini_project.vo.DiaryTagVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import static com.kh.mini_project.common.DiaryTagQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryTagDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(DiaryTagVo vo) {
        // INSERT 실패 경우의 수
        // 1. SQL 구문 오류 => DataAccessException
        // 2. 서브쿼리를 조건으로 사용했으나, 서브쿼리가 아무런 튜플을 반환하지 않은 경우 => update 메서드는 0을 반환

        // 서브 쿼리를 사용하지 않으므로, update 리턴 값 검증 로직 작성 X
        jdbcTemplate.update(INSERT_QUERY, vo.getDiaryNum(), vo.getTagName());
    }

    public List<DiaryTagVo> selectByDiaryNum(int diaryNum) {
        return jdbcTemplate.query(SELECT_BY_DIARY_NUM_QUERY, new Object[]{diaryNum}, (rs, rowNum) -> new DiaryTagVo(
                rs.getInt("DIARY_TAG_NUM"),
                rs.getInt("DIARY_NUM"),
                rs.getString("TAG_NAME")
        ));
    }

    public void deleteByDiaryNum(int diaryNum) {
        jdbcTemplate.update(DELETE_BY_DIARY_NUM_QUERY, diaryNum);
    }
}
