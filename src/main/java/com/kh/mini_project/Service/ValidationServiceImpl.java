package com.kh.mini_project.Service;

import com.kh.mini_project.Dao.MemberDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationServiceImpl implements ValidationService {
    private final MemberDao memberDao;
    private static final Set<String> MEMBER_UNIQUE_FIELDS = Set.of("id", "email", "nickname");

    public boolean isUnique(String field, String value) {
        if (!MEMBER_UNIQUE_FIELDS.contains(field.toLowerCase())) {
            log.info("\"field\"은/는 UNIQUE 제약조건이 설정되지 않은 필드이거나, 존재하지 않는 필드입니다.");
            throw new IllegalArgumentException();
        }
        return memberDao.selectCount(field, value) == 0;
    }
}
