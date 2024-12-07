package com.kh.mini_project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteMemberRequest {
    private String id;
    private String password;
}
