package com.kh.mini_project.Dao;

import com.kh.mini_project.Vo.ProgrammingLanguageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.Common.ProgrammingLanguageQuery.*;

@Repository
@RequiredArgsConstructor
public class ProgrammingLanguageDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(ProgrammingLanguageVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getName());
    }
}
