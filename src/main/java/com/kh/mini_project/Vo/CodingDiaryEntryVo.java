package com.kh.mini_project.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodingDiaryEntryVo {
    private Integer codingDiaryEntryNum;
    private Integer codingDiaryNum;
    private String type;
    private String programmingLanguageName;
    private String content;
    private Integer sequence;
}
