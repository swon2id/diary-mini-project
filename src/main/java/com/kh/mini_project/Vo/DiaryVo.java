package com.kh.mini_project.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryVo {
    private Integer diaryNum;
    private Integer memberNum;
    private String title;
    private String content;
    private LocalDateTime writtenDate;
}
