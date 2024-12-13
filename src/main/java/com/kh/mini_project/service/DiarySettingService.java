package com.kh.mini_project.service;

import com.kh.mini_project.dao.DiarySettingDao;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.DiarySettingDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.vo.DiarySettingVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DiarySettingService {
    private final MemberDao memberDao;
    private final DiarySettingDao diarySettingDao;

    @Transactional
    public void updateDiarySetting(AuthenticateLoginRequest loginDto, DiarySettingDto updatedDiarySettingDto) {
        log.info("updateDiarySetting called for user: {} with settings: {}", loginDto.getId(), updatedDiarySettingDto);

        int memberNum = getMemberNumOrThrow(loginDto);
        log.debug("Found memberNum: {}", memberNum);

        DiarySettingVo existingVo = diarySettingDao.selectByMemberNum(memberNum);
        if (existingVo == null) {
            // 만약 존재하는 기록이 없다면 새로운 기록 넣어줘야 한다 (디버그 체킹, 현재 업데이트 로직에서 아무것도 없을때는 못건드리므로)
            DiarySettingVo newVo = new DiarySettingVo();
            newVo.setMemberNum(memberNum);
            newVo.setCurrentTheme(updatedDiarySettingDto.getTheme());
            newVo.setCurrentFont(updatedDiarySettingDto.getFont());
            newVo.setCurrentMainBannerImage(updatedDiarySettingDto.getMainBannerImage());
            newVo.setCurrentAlertSound(updatedDiarySettingDto.getAlertSound());

            diarySettingDao.insert(newVo);
            log.info("No existing diary setting found. Inserted new one for memberNum: {}", memberNum);
            // 이렇게 추가함으로서 이제 필요시 업데이트가 가능
        } else {
            // 업데이트할 필드만 선택
            Map<String, Object> fieldsToUpdate = new HashMap<>();
            if (updatedDiarySettingDto.getTheme() != null) {
                fieldsToUpdate.put("CURRENT_THEME", updatedDiarySettingDto.getTheme());
            }
            if (updatedDiarySettingDto.getFont() != null) {
                fieldsToUpdate.put("CURRENT_FONT", updatedDiarySettingDto.getFont());
            }
            if (updatedDiarySettingDto.getMainBannerImage() != null) {
                fieldsToUpdate.put("CURRENT_MAIN_BANNER_IMAGE", updatedDiarySettingDto.getMainBannerImage());
            }
            if (updatedDiarySettingDto.getAlertSound() != null) {
                fieldsToUpdate.put("CURRENT_ALERT_SOUND", updatedDiarySettingDto.getAlertSound());
            }

            if (!fieldsToUpdate.isEmpty()) {
                boolean updateResult = diarySettingDao.update(memberNum, fieldsToUpdate);
                if (!updateResult) {
                    log.warn("Failed to update diary setting for memberNum: {}", memberNum);
                    throw new EmptyResultDataAccessException("업데이트 된 일기 설정이 존재하지 않습니다.", 1);
                }
                log.info("Successfully updated diary setting for memberNum: {}", memberNum);
            } else {
                log.info("No fields provided for update. Skipping update for memberNum: {}", memberNum);
            }
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
