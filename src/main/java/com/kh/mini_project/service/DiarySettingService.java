package com.kh.mini_project.service;

import com.kh.mini_project.dao.DiarySettingDao;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.DiarySettingDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.vo.DiarySettingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiarySettingService {
    private final MemberDao memberDao;
    private final DiarySettingDao diarySettingDao;

    @Transactional
    public void updateDiarySetting(AuthenticateLoginRequest loginDto, DiarySettingDto updatedDiarySettingDto) {
        int memberNum = getMemberNumOrThrow(loginDto);

        if (!diarySettingDao.update(memberNum, updatedDiarySettingDto.getTheme(), updatedDiarySettingDto.getFont(), updatedDiarySettingDto.getMainBannerImage(), updatedDiarySettingDto.getAlertSound())) {
            throw new EmptyResultDataAccessException("업데이트 된 일기 설정이 존재하지 않습니다.", 1);
        }
    }

    public DiarySettingDto getDiarySetting(AuthenticateLoginRequest loginDto) {
        int memberNum = getMemberNumOrThrow(loginDto);

        DiarySettingVo diarySettingVo = diarySettingDao.selectByMemberNum(memberNum);
        if (diarySettingVo == null) return null;

        DiarySettingDto diarySettingDto = new DiarySettingDto();
        diarySettingDto.setFont(diarySettingVo.getCurrentFont());
        diarySettingDto.setTheme(diarySettingVo.getCurrentTheme());
        diarySettingDto.setMainBannerImage(diarySettingVo.getCurrentMainBannerImage());
        diarySettingDto.setAlertSound(diarySettingVo.getCurrentAlertSound());
        return diarySettingDto;
    }

    /**
     * 이하는 코드 중복 제거 및 가독성을 높이기 위한 헬퍼 메서드 입니다.
     * */
    private int getMemberNumOrThrow(AuthenticateLoginRequest loginDto) {
        int memberNum = memberDao.selectMemberNumById(loginDto.getId());
        if (memberNum == -1) {
            throw new IllegalArgumentException("ID, PW는 인증되었지만, ID로 memberNum을 조회하는데 실패하였습니다.");
        }
        return memberNum;
    }
}
