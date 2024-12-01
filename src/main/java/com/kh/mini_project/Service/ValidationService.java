package com.kh.mini_project.Service;

import com.kh.mini_project.Dao.MemberDao;
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
     * @param field 확인하려는 컬럼
     * @param value 확인하려는 컬럼의 값
     * @return 중복 여부 boolean
     */
    public boolean isUnique(String field, String value) {
        return memberDao.selectCountByFieldAndValue(field, value) == 0;
    }
}
