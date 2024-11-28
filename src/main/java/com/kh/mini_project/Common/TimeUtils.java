package com.kh.mini_project.Common;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Seoul")));
    }
}