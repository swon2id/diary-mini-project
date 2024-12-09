package com.kh.mini_project.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationVo {
    private Integer notificationNum;
    private Integer scheduleNum;
    private LocalDateTime alertTime;
    private String alertMethod;
}
