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
