package com.kh.mini_project.Service;

import com.kh.mini_project.Common.TimeUtils;
import com.kh.mini_project.Dao.MemberDao;
import com.kh.mini_project.Dto.SignUpRequestDto;
import com.kh.mini_project.Vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberDao memberDao;

    /**
     * 회원가입을 처리합니다.
     *
     * @param signUpRequestDto 회원 가입에 필요한 4개 정보가 담긴 객체
     * @return void
     */
    public void signUp(SignUpRequestDto signUpRequestDto) {
        MemberVo memberVo = new MemberVo();
        memberVo.setId(signUpRequestDto.getId());
        memberVo.setPassword(signUpRequestDto.getPassword());
        memberVo.setEmail(signUpRequestDto.getEmail());
        memberVo.setNickname(signUpRequestDto.getNickname());
        memberVo.setRegistrationDate(TimeUtils.getCurrentTimestamp());
        memberDao.insert(memberVo);
    }
}
