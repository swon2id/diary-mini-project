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
        jdbcTemplate.update(INSERT_QUERY, vo.getCodingDiaryNum(), vo.getType(), vo.getProgrammingLanguageName(), vo.getContent(), vo.getSequence());
    }

    public List<CodingDiaryEntryVo> selectByCodingDiaryNum(int codingDiaryNum) {
        return jdbcTemplate.query(SELECT_BY_CODING_DIARY_NUM, new Object[]{codingDiaryNum}, (rs, rowNum) -> new CodingDiaryEntryVo(
                rs.getInt("CODING_DIARY_ENTRY_NUM"),
                rs.getInt("CODING_DIARY_NUM"),
                rs.getString("TYPE"),
                rs.getString("PROGRAMMING_LANGUAGE_NAME"),
                rs.getString("CONTENT"),
                rs.getInt("SEQUENCE")
        ));
    }
}
