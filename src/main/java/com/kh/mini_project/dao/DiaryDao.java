package com.kh.mini_project.dao;

import com.kh.mini_project.vo.DiaryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static com.kh.mini_project.common.DiaryQuery.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class DiaryDao {
    private final JdbcTemplate jdbcTemplate;

    public Integer insertAndReturnPk(DiaryVo vo) {
        // 생성된 pk 값을 얻기 위한 KeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"DIARY_NUM"});
            ps.setInt(1, vo.getMemberNum());
            ps.setString(2, vo.getTitle());
            ps.setString(3, vo.getContent());
            ps.setObject(4, vo.getWrittenDate());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : null;
    }

    public List<DiaryVo> selectByMemberNumAndDate(int memberNum, String year, String month) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_NUM_AND_DATE_QUERY, new Object[]{memberNum, year, month}, (rs, rowNum) -> new DiaryVo(
                rs.getInt("DIARY_NUM"),
                rs.getInt("MEMBER_NUM"),
                rs.getString("TITLE"),
                rs.getString("CONTENT"),
                rs.getTimestamp("WRITTEN_DATE").toLocalDateTime()
        ));
    }

    public DiaryVo selectByDiaryNum(int diaryNum) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_DIARY_NUM_QUERY, new Object[]{diaryNum}, (rs, rowNum) -> new DiaryVo(
                    rs.getInt("DIARY_NUM"),
                    rs.getInt("MEMBER_NUM"),
                    rs.getString("TITLE"),
                    rs.getString("CONTENT"),
                    rs.getTimestamp("WRITTEN_DATE").toLocalDateTime()
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    public boolean update(int diaryNum, String title, String content, LocalDateTime writtenDate) {
        return 1 == jdbcTemplate.update(UPDATE_QUERY, title, content, writtenDate, diaryNum);
    }

    public List<DiaryVo> selectByMemberNum(int memberNum) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_NUM_QUERY, new Object[]{memberNum}, (rs, rowNum) -> new DiaryVo(
                rs.getInt("DIARY_NUM"),
                rs.getInt("MEMBER_NUM"),
                rs.getString("TITLE"),
                rs.getString("CONTENT"),
                rs.getTimestamp("WRITTEN_DATE").toLocalDateTime()
        ));
    }

    public void deleteByDiaryNum(int diaryNum) {
        jdbcTemplate.update("DELETE FROM DIARY WHERE DIARY_NUM = ?", diaryNum);
    }

    public static final String SELECT_ALL_DIARY = "" +
            "SELECT * FROM diary " +
            "WHERE member_num = (" +
            "   SELECT member_num FROM member " +
            "   WHERE id = ?) " +
            "ORDER BY written_date ASC";

    // 해당 회원의 일기 전체 조회
    public List<DiaryVo> diaryListByMember(String userId) {
        try {
            return jdbcTemplate.query((SELECT_ALL_DIARY), new Object[]{userId}, new DiaryRowMapper());
        } catch (DataAccessException e) {
            log.error("다이어리 조회 중 에러 발생. ", e);
            throw e;
        }
    }

    // Row Mapper
    private static class DiaryRowMapper implements RowMapper<DiaryVo> {

        @Override
        public DiaryVo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DiaryVo(
                    rs.getInt("diary_num"),
                    rs.getInt("member_num"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getTimestamp("written_date").toLocalDateTime()
            );
        }
    }
}