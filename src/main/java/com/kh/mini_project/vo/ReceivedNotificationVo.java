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
public class ReceivedNotificationVo {
    private Integer receivedNotificationNum;
    private Integer scheduleNum;
    private Integer memberNum;
    private LocalDateTime publishedAt;
    private String alertMethod;
    private boolean isConfirmed;
}
