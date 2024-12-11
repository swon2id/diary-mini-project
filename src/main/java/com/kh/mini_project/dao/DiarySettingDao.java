package com.kh.mini_project.dao;

import com.kh.mini_project.vo.DiarySettingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiarySettingDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(DiarySettingVo vo) {
        // INSERT 실패 경우의 수
        // 1. SQL 구문 오류 => DataAccessException
        // 2. 서브쿼리를 조건으로 사용했으나, 서브쿼리가 아무런 튜플을 반환하지 않은 경우 => update 메서드는 0을 반환

        // 서브 쿼리를 사용하지 않으므로, update 리턴 값 검증 로직 작성 X
        String INSERT_QUERY = "INSERT INTO DIARY_SETTING VALUES(SEQ_DIARY_SETTING.NEXTVAL, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(INSERT_QUERY, vo.getMemberNum(), vo.getCurrentTheme(), vo.getCurrentFont(), vo.getCurrentMainBannerImage(), vo.getCurrentAlertSound());
    }

    public DiarySettingVo selectByMemberNum(int memberNum) {
        String SELECT_BY_MEMBER_NUM_QUERY = "SELECT * FROM DIARY_SETTING WHERE MEMBER_NUM = ?";
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_MEMBER_NUM_QUERY, new Object[]{memberNum}, (rs, rowNum) -> new DiarySettingVo(
                    rs.getInt("DIARY_SETTING_NUM"),
                    rs.getInt("MEMBER_NUM"),
                    rs.getString("CURRENT_THEME"),
                    rs.getString("CURRENT_FONT"),
                    rs.getString("CURRENT_MAIN_BANNER_IMAGE"),
                    rs.getString("CURRENT_ALERT_SOUND")
            ));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean update(int memberNum, String theme, String font, String bannerImage, String alertSound) {
        String UPDATE_QUERY = "UPDATE DIARY_SETTING SET CURRENT_THEME = ?, CURRENT_FONT = ?, CURRENT_MAIN_BANNER_IMAGE = ?, CURRENT_ALERT_SOUND = ? WHERE MEMBER_NUM =?";
        return 1 == jdbcTemplate.update(UPDATE_QUERY, theme, font, bannerImage, alertSound, memberNum);
    }
}
