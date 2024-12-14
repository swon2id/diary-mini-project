package com.kh.mini_project.dao;

import com.kh.mini_project.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// import static 이란?
// 클래스명을 생략하고 static 멤버 접근 가능
import java.sql.PreparedStatement;

import static com.kh.mini_project.common.MemberQuery.*;


@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberDao {

    private final JdbcTemplate jdbcTemplate;

    public Integer insertAndReturnPk(MemberVo vo) {
        // 생성된 pk 값을 얻기 위한 KeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"MEMBER_NUM"});
            ps.setString(1, vo.getId());
            ps.setString(2, vo.getPassword());
            ps.setString(3, vo.getEmail());
            ps.setString(4, vo.getNickname());
            ps.setObject(5, vo.getRegistrationDate());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : null;
    }

    public void deleteMemberData(Integer memberNum) {
        // 참조 데이터 삭제
        jdbcTemplate.update(DELETE_DIARY_TAG_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_CODING_DIARY_ENTRY_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_CODING_DIARY_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_DIARY_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_NOTIFICATION_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_SCHEDULE_NOTIFICATIONS_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_SCHEDULE_BY_MEMBER_NUM, memberNum);
        jdbcTemplate.update(DELETE_DIARY_SETTINGS_BY_MEMBER_NUM, memberNum);

        // 회원 삭제
        jdbcTemplate.update(DELETE_MEMBER_BY_MEMBER_NUM, memberNum);
    }

    public MemberVo selectById(String id) {
        String sql = "SELECT * FROM MEMBER WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> new MemberVo(
                rs.getInt("MEMBER_NUM"),
                rs.getString("ID"),
                rs.getString("PASSWORD"),
                rs.getString("EMAIL"),
                rs.getString("NICKNAME"),
                rs.getTimestamp("REGISTRATION_DATE").toLocalDateTime()
        ));
    }

    // 회원 정보 수정
    @Transactional
    public void updateMemberInfo(String id, String email, String nickname, String password) {
        String sql = "UPDATE MEMBER SET EMAIL = ?, NICKNAME = ?, PASSWORD = ? WHERE ID = ?";
        try {
            int rowsUpdated = jdbcTemplate.update(sql, email, nickname, password, id);
        } catch (Exception e) {
            log.error("Error during update operation", e);
            throw e;  // 예외가 발생하면 롤백되도록
        }
    }

    public int selectCountByFieldAndValue(String field, String value) {

        Integer count = jdbcTemplate.queryForObject(String.format(SELECT_COUNT_BY_FIELD_AND_VALUE_QUERY, field.toUpperCase()), new Object[]{value}, Integer.class);
        return count == null ? 0 : count;
    }

    public int selectCountByIdAndPassword(String id, String password) {
        Integer count = jdbcTemplate.queryForObject(SELECT_COUNT_BY_ID_AND_PASSWORD_QUERY, new Object[]{id, password}, Integer.class);
        return count == null ? 0 : count;
    }

    public int selectMemberNumById(String id) {
        Integer memberNum = jdbcTemplate.queryForObject(SELECT_MEMBER_NUM_BY_ID_QUERY, new Object[]{id}, Integer.class);
        return memberNum == null ? -1 : memberNum;
    }
}
