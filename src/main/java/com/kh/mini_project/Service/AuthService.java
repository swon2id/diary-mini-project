package com.kh.mini_project.Service;

import com.kh.mini_project.Dto.RegisterRequestDto;

public interface AuthService {
    /**
     * 회원가입을 처리합니다.
     *
     * @param registerRequestDto 회원 정보가 담긴 registerRequestDto 객체
     * @return void
     */
    void registerMember(RegisterRequestDto registerRequestDto);
}