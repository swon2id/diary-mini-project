package com.kh.mini_project.common;

public class ScheduleQuery {
    public static final String INSERT_QUERY = "INSERT INTO SCHEDULE (SCHEDULE_NUM, MEMBER_NUM, TITLE, DESCRIPTION, START_DATE, END_DATE, IS_ALLDAY, IS_IMPORTANT) VALUES (SEQ_SCHEDULE.NEXTVAL, ?, ?, ?, ?, ?, ? ,?)";
}
