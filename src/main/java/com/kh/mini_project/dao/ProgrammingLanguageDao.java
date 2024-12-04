package com.kh.mini_project.dao;

import com.kh.mini_project.vo.ProgrammingLanguageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.common.ProgrammingLanguageQuery.*;

@Repository
@RequiredArgsConstructor
public class ProgrammingLanguageDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(ProgrammingLanguageVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getName());
    }
}
