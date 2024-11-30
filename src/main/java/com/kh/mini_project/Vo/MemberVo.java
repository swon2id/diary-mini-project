package com.kh.mini_project.Vo;

import lombok.*;

import java.sql.Timestamp;

// getter, setter, toString, equals, hashCode를 자동으로 생성
@Data

@NoArgsConstructor
@AllArgsConstructor
public class MemberVo {
    private Integer memberNum;
    private String id;
    private String password;
    private String email;
    private String nickname;
    private Timestamp registrationDate;
}
