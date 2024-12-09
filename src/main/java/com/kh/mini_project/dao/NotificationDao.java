package com.kh.mini_project.dao;

import com.kh.mini_project.vo.NotificationVo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.kh.mini_project.common.NotificationQuery.INSERT_QUERY;

@Repository
@RequiredArgsConstructor
public class NotificationDao {
    private final JdbcTemplate jdbcTemplate;

    public void insert(NotificationVo vo) {
        jdbcTemplate.update(INSERT_QUERY, vo.getScheduleNum(), vo.getAlertTime(), vo.getAlertMethod());
    }
}
