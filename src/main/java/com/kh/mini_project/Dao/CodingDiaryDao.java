package com.kh.mini_project.Dao;

import com.kh.mini_project.Vo.CodingDiaryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

import static com.kh.mini_project.Common.CodingDiaryQuery.*;

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
}
