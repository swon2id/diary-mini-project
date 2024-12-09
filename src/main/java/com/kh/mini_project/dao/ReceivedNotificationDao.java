package com.kh.mini_project.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.common.ReceivedNotificationQuery.DELETE_BY_SCHEDULE_NUM_QUERY;

@Repository
@RequiredArgsConstructor
public class ReceivedNotificationDao {
    private final JdbcTemplate jdbcTemplate;

    public void deleteByScheduleNum(int scheduleNum) {
        jdbcTemplate.update(DELETE_BY_SCHEDULE_NUM_QUERY, scheduleNum);
    }
}
