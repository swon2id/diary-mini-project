package com.kh.mini_project.dao;

import com.kh.mini_project.vo.CodingDiaryEntryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.common.CodingDiaryEntryQuery.*;

@Repository
@RequiredArgsConstructor
public class CodingDiaryEntryDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(CodingDiaryEntryVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getCodingDiaryNum(), vo.getType(), vo.getProgrammingLanguageName(), vo.getContent(), vo.getSequence());
    }
}
