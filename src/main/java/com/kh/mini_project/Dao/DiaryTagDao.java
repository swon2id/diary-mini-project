package com.kh.mini_project.Dao;

import com.kh.mini_project.Vo.DiaryTagVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.Common.DiaryTagQuery.*;

@Repository
@RequiredArgsConstructor
public class DiaryTagDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(DiaryTagVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getDiaryNum(), vo.getTagName());
    }
}
