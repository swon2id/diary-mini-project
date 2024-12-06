package com.kh.mini_project.dao;

import com.kh.mini_project.vo.DiaryTagVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.kh.mini_project.common.DiaryTagQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryTagDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(DiaryTagVo vo) {
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
