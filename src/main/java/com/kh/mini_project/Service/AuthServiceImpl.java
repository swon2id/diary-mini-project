package com.kh.mini_project.Service;

import com.kh.mini_project.Common.TimeUtils;
import com.kh.mini_project.Dao.MemberDao;
import com.kh.mini_project.Dto.RegisterRequestDto;
import com.kh.mini_project.Vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberDao memberDao;

    @Override
    public void registerMember(RegisterRequestDto registerRequestDto) {
        MemberVo memberVo = new MemberVo();
        memberVo.setId(registerRequestDto.getId());
        memberVo.setPassword(registerRequestDto.getPassword());
        memberVo.setEmail(registerRequestDto.getEmail());
        memberVo.setNickname(registerRequestDto.getNickname());
        memberVo.setRegistrationDate(TimeUtils.getCurrentTimestamp());
        memberDao.insert(memberVo);
    }
}
