package com.kh.mini_project.Service;

public interface ValidationService {
    /**
     * 회원가입 과정에서 key 중복을 확인합니다.
     *
     * @param field 확인하려는 컬럼
     * @param value 확인하려는 컬럼의 값
     * @return boolean
     */
    boolean isUnique(String field, String value);
}
