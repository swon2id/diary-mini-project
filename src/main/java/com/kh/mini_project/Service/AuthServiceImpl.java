package com.kh.mini_project.Service;

import com.kh.mini_project.Common.TimeUtils;
import com.kh.mini_project.Dao.MemberDao;
import com.kh.mini_project.Dto.SignUpRequestDto;
import com.kh.mini_project.Vo.MemberVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberDao memberDao;

    @Override
    public void signUp(SignUpRequestDto registerRequestDto) {
        MemberVo memberVo = new MemberVo();
        memberVo.setId(registerRequestDto.getId());
        memberVo.setPassword(registerRequestDto.getPassword());
        memberVo.setEmail(registerRequestDto.getEmail());
        memberVo.setNickname(registerRequestDto.getNickname());
        memberVo.setRegistrationDate(TimeUtils.getCurrentTimestamp());
        memberDao.insert(memberVo);
    }
}
