package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.LoginAuthenticationRequestDto;
import com.kh.mini_project.dto.SignUpRequestDto;
import com.kh.mini_project.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDao memberDao;

    /**
     * 회원가입을 처리합니다.
     *
     * @param dto 회원 가입에 필요한 4개 정보가 담긴 dto
     * @return void
     */
    public void signUp(SignUpRequestDto dto) {
        MemberVo memberVo = new MemberVo();
        memberVo.setId(dto.getId());
        memberVo.setPassword(dto.getPassword());
        memberVo.setEmail(dto.getEmail());
        memberVo.setNickname(dto.getNickname());
        memberVo.setRegistrationDate(TimeUtils.getCurrentLocalDateTime());
        memberDao.insert(memberVo);
    }

    /**
     * 로그인 시도에 대해 신원을 검증합니다.
     *
     * @param dto 로그인을 위한 ID, PASSWORD가 담긴 dto
     * @return 검증 성공 여부 boolean
     */
    public boolean validateCredentials(LoginAuthenticationRequestDto dto) {
        return memberDao.selectCountByIdAndPassword(dto.getId(), dto.getPassword()) == 1;
    }
}