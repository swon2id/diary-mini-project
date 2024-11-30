package com.kh.mini_project.Service;

import com.kh.mini_project.Dto.SignUpRequestDto;

public interface AuthService {
    /**
     * 회원가입을 처리합니다.
     *
     * @param signUpRequestDto 회원 가입에 필요한 4개 정보가 담긴 객체
     * @return void
     */
    void signUp(SignUpRequestDto signUpRequestDto);
}