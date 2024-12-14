package com.kh.mini_project.dao;

import com.kh.mini_project.vo.NotificationVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kh.mini_project.common.NotificationQuery.*;

@Repository
@RequiredArgsConstructor
public class NotificationDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(NotificationVo vo) {
        // INSERT 실패 경우의 수
        // 1. SQL 구문 오류 => DataAccessException
        // 2. 서브쿼리를 조건으로 사용했으나, 서브쿼리가 아무런 튜플을 반환하지 않은 경우 => update 메서드는 0을 반환

        // 서브 쿼리를 사용하지 않으므로, update 리턴 값 검증 로직 작성 X
        jdbcTemplate.update(INSERT_QUERY, vo.getScheduleNum(), vo.getAlertTime(), vo.getAlertMethod());
    }

    public void deleteByScheduleNum(int scheduleNum) {
        jdbcTemplate.update(DELETE_BY_SCHEDULE_NUM, scheduleNum);
    }

    public List<NotificationVo> selectByScheduleNum(int scheduleNum) {
        return jdbcTemplate.query(SELECT_BY_SCHEDULE_NUM, new Object[]{scheduleNum}, (rs, rowNum) -> new NotificationVo(
                rs.getInt("NOTIFICATION_NUM"),
                rs.getInt("SCHEDULE_NUM"),
                rs.getTimestamp("ALERT_TIME").toLocalDateTime(),
                rs.getString("ALERT_METHOD")
        ));
    }
}
