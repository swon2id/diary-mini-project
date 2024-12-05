package com.kh.mini_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyDiaryEntryDto {
    private String diaryNum;
    private String title;
    private String content;
    private List<String> tags;
    private String writtenDate;
}
