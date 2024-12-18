package com.kh.mini_project.common;

public class ScheduleQuery {
    public static final String INSERT_QUERY = "INSERT INTO SCHEDULE (SCHEDULE_NUM, MEMBER_NUM, TITLE, DESCRIPTION, START_DATE, END_DATE, IS_ALLDAY, IS_IMPORTANT) VALUES (SEQ_SCHEDULE.NEXTVAL, ?, ?, ?, ?, ?, ? ,?)";
    public static final String SELECT_SCHEDULE_NUM_BY_MEMBER_NUM_QUERY = "SELECT SCHEDULE_NUM FROM SCHEDULE WHERE MEMBER_NUM = ?";
    public static final String UPDATE_QUERY = "UPDATE SCHEDULE SET TITLE = ?, DESCRIPTION = ?, START_DATE = ?, END_DATE = ?, IS_ALLDAY = ?, IS_IMPORTANT = ? WHERE SCHEDULE_NUM = ?";
    public static final String DELETE_BY_SCHEDULE_NUM = "DELETE FROM SCHEDULE WHERE SCHEDULE_NUM = ?";
    public static final String SELECT_BY_MEMBER_NUM_AND_DATE_QUERY = "" +
        "SELECT * FROM SCHEDULE " +
        "WHERE MEMBER_NUM = ? " +
        "AND START_DATE <= LAST_DAY(TO_DATE(?, 'YYYYMM')) " +
        "AND END_DATE >= TO_DATE(?, 'YYYYMM')";
}
