package com.kh.mini_project.Dao;

import com.kh.mini_project.Vo.CodingDiaryEntryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.Common.CodingDiaryEntryQuery.*;

@Repository
@RequiredArgsConstructor
public class CodingDiaryEntryDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(CodingDiaryEntryVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getCodingDiaryNum(), vo.getType(), vo.getProgrammingLanguageName(), vo.getContent(), vo.getSequence());
    }
}
