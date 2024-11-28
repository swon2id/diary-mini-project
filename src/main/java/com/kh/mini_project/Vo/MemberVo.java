package com.kh.mini_project.Vo;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
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
