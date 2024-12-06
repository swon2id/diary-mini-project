package com.kh.mini_project.dto;

import lombok.Data;
import java.util.LinkedHashSet;

@Data
public class MonthlyDiaryEntryDto {
    private String diaryNum;
    private String title;
    private String content;
    private LinkedHashSet<String> tags;
    private String writtenDate;
}
