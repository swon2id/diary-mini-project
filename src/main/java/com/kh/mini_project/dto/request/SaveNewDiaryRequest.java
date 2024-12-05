package com.kh.mini_project.dto.request;

import com.kh.mini_project.dto.DiaryDto;
import jakarta.validation.Valid;
import lombok.Data;

import java.util.List;

@Data
public class SaveNewDiaryRequest {
    @Valid
    private AuthenticateLoginRequest loggedInMember;

    @Valid
    private DiaryDto newDiary;
}
