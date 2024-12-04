package com.kh.mini_project.Service;

import com.kh.mini_project.Common.TimeUtils;
import com.kh.mini_project.Dao.*;
import com.kh.mini_project.Dto.DiarySaveRequestDto;
import com.kh.mini_project.Dto.LoginAuthenticationRequestDto;
import com.kh.mini_project.Vo.CodingDiaryEntryVo;
import com.kh.mini_project.Vo.CodingDiaryVo;
import com.kh.mini_project.Vo.DiaryTagVo;
import com.kh.mini_project.Vo.DiaryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final MemberDao memberDao;
    private final DiaryDao diaryDao;
    private final DiaryTagDao diaryTagDao;
    private final CodingDiaryDao codingDiaryDao;
    private final CodingDiaryEntryDao codingDiaryEntryDao;

    // 예외 발생시 자동 롤백
    @Transactional
    public void saveNewDiary(LoginAuthenticationRequestDto loginDto, DiarySaveRequestDto.DiaryDto diaryDto) {
        int memberNum = memberDao.selectMemberNumById(loginDto.getId());
        if (memberNum == -1) {
            throw new IllegalArgumentException("ID, PW는 인증되었지만, ID로 memberNum을 조회하는데 실패하였습니다.");
        }

        DiaryVo diaryVo = new DiaryVo();
        diaryVo.setMemberNum(memberNum);
        diaryVo.setTitle(diaryDto.getTitle());
        diaryVo.setContent(diaryDto.getContent());
        diaryVo.setWrittenDate(TimeUtils.convertToLocalDateTime(diaryDto.getWrittenDate()));
        Integer diaryPrimaryKey = diaryDao.insertAndReturnPk(diaryVo);
        if (diaryPrimaryKey == null) {
            throw new NullPointerException("일기가 생성되었지만, PK를 얻는데 실패하였습니다.");
        }

        List<String> diaryTags = diaryDto.getTags();
        for (String tag: diaryTags) {
            DiaryTagVo diaryTagVo = new DiaryTagVo();
            diaryTagVo.setDiaryNum(diaryPrimaryKey);
            diaryTagVo.setTagName(tag);
            diaryTagDao.insert(diaryTagVo);
        }

        List<DiarySaveRequestDto.DiaryDto.CodingDiaryEntryDto> codingDiaryEntries = diaryDto.getCodingDiaryEntries();
        if (codingDiaryEntries != null) {
            CodingDiaryVo codingDiaryVo = new CodingDiaryVo();
            codingDiaryVo.setDiaryNum(diaryPrimaryKey);
            Integer codingDiaryPrimaryKey = codingDiaryDao.insertAndReturnPk(codingDiaryVo);
            if (codingDiaryPrimaryKey == null) {
                throw new NullPointerException("코딩일기가 생성되었지만, PK를 얻는데 실패하였습니다.");
            }

            for (var entry: codingDiaryEntries) {
                CodingDiaryEntryVo codingDiaryEntryVo = new CodingDiaryEntryVo();
                codingDiaryEntryVo.setCodingDiaryNum(codingDiaryPrimaryKey);
                codingDiaryEntryVo.setType(entry.getProgrammingLanguageName() == null ? "comment" : "snippet");
                codingDiaryEntryVo.setProgrammingLanguageName(entry.getProgrammingLanguageName() == null ? null : entry.getProgrammingLanguageName().toLowerCase());
                codingDiaryEntryVo.setContent(entry.getContent());
                codingDiaryEntryVo.setSequence(entry.getSequence());
                codingDiaryEntryDao.insert(codingDiaryEntryVo);
            }
        }
    }
}
