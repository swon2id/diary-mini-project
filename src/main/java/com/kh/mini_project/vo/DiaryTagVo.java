package com.kh.mini_project.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryTagVo {
    private Integer diaryTagNum;
    private Integer diaryNum;
    private String tagName;
}
