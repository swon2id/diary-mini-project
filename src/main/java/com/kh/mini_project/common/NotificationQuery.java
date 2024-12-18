package com.kh.mini_project.common;

public class NotificationQuery {
    public static final String INSERT_QUERY = "INSERT INTO NOTIFICATION (NOTIFICATION_NUM, SCHEDULE_NUM, ALERT_TIME, ALERT_METHOD) VALUES(SEQ_NOTIFICATION.NEXTVAL, ?, ?, ?)";
    public static final String DELETE_BY_SCHEDULE_NUM = "DELETE FROM NOTIFICATION WHERE SCHEDULE_NUM = ?";
    public static final String SELECT_BY_SCHEDULE_NUM = "SELECT * FROM NOTIFICATION WHERE SCHEDULE_NUM = ?";
}
