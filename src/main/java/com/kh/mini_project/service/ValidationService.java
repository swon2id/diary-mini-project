package com.kh.mini_project.service;

import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.request.CheckUniqueStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {
    private final MemberDao memberDao;

    /**
     * 회원가입 과정에서 key 중복을 확인합니다.
     *
     * @param dto 필드와 벨류를 유지하는 객체
     * @return 중복 여부 boolean
     */
    public boolean isUnique(CheckUniqueStatusRequest dto) {
        return memberDao.selectCountByFieldAndValue(dto.getField(), dto.getValue()) == 0;
    }
}
