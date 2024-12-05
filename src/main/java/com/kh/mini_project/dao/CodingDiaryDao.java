package com.kh.mini_project.dao;

import com.kh.mini_project.vo.CodingDiaryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;

import static com.kh.mini_project.common.CodingDiaryQuery.*;

@Repository
@RequiredArgsConstructor
public class CodingDiaryDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer insertAndReturnPk(CodingDiaryVo vo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"CODING_DIARY_NUM"});
            ps.setInt(1, vo.getDiaryNum());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : null;
    }

    public CodingDiaryVo selectByDiaryNum(int diaryNum) {
        try {
            return jdbcTemplate.queryForObject(
                    SELECT_BY_DIARY_NUM,
                    new Object[]{diaryNum},
                    (rs, rowNum) -> new CodingDiaryVo(
                            rs.getInt("CODING_DIARY_NUM"),
                            rs.getInt("DIARY_NUM")
                    )
            );
        }
        // 결과가 없으면 null 반환
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
