package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.DiarySettingDao;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.dto.request.SignUpRequest;
import com.kh.mini_project.vo.DiarySettingVo;
import com.kh.mini_project.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDao memberDao;
    private final DiarySettingDao diarySettingDao;

    /**
     * 회원가입을 처리합니다.
     *
     * @param dto 회원 가입에 필요한 4개 정보가 담긴 dto
     * @return void
     */
    @Transactional
    public void signUp(SignUpRequest dto) {
        MemberVo memberVo = new MemberVo();
        memberVo.setId(dto.getId());
        memberVo.setPassword(dto.getPassword());
        memberVo.setEmail(dto.getEmail());
        memberVo.setNickname(dto.getNickname());
        memberVo.setRegistrationDate(TimeUtils.getCurrentLocalDateTime());

        Integer memberNum = memberDao.insertAndReturnPk(memberVo);
        if (memberNum == null) {
            throw new NullPointerException("회원이 생성되었지만, PK를 얻는데 실패하였습니다.");
        }

        DiarySettingVo diarySettingVo = new DiarySettingVo();
        diarySettingVo.setMemberNum(memberNum);
        diarySettingVo.setCurrentFont("default");
        diarySettingVo.setCurrentTheme("default");
        diarySettingVo.setCurrentMainBannerImage("default");
        diarySettingVo.setCurrentAlertSound("default");

        diarySettingDao.insert(diarySettingVo);
    }

    /**
     * 로그인 시도에 대해 신원을 검증합니다.
     *
     * @param dto 로그인을 위한 ID, PASSWORD가 담긴 dto
     * @return 검증 성공 여부 boolean
     */
    public boolean validateCredentials(AuthenticateLoginRequest dto) {
        return memberDao.selectCountByIdAndPassword(dto.getId(), dto.getPassword()) == 1;
    }
}