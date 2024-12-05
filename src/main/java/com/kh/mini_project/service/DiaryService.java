package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.*;
import com.kh.mini_project.dto.DiaryDto;
import com.kh.mini_project.dto.LoginAuthenticationRequestDto;
import com.kh.mini_project.dto.MonthlyDiaryListRequestDto;
import com.kh.mini_project.vo.CodingDiaryEntryVo;
import com.kh.mini_project.vo.CodingDiaryVo;
import com.kh.mini_project.vo.DiaryTagVo;
import com.kh.mini_project.vo.DiaryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.*;

@Slf4j
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
    public void saveNewDiary(LoginAuthenticationRequestDto loginDto, DiaryDto diaryDto) {
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
        if (diaryTags != null) {
            for (String tag: diaryTags) {
                DiaryTagVo diaryTagVo = new DiaryTagVo();
                diaryTagVo.setDiaryNum(diaryPrimaryKey);
                diaryTagVo.setTagName(tag);
                diaryTagDao.insert(diaryTagVo);
            }
        }

        List<DiaryDto.CodingDiaryEntryDto> codingDiaryEntries = diaryDto.getCodingDiaryEntries();
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

    public List<DiaryDto> getMonthlyDiaryList(LoginAuthenticationRequestDto loginDto, MonthlyDiaryListRequestDto.DateDto dateDto) {
        int memberNum = memberDao.selectMemberNumById(loginDto.getId());
        if (memberNum == -1) {
            throw new IllegalArgumentException("ID, PW는 인증되었지만, ID로 memberNum을 조회하는데 실패하였습니다.");
        }

        // 1단계: 일기 목록 조회
        String month = dateDto.getMonth();
        if (month.length() == 1 && month.matches("[1-9]")) {
            month = "0" + month;
        }
        List<DiaryVo> diaryVoList = diaryDao.selectByIdAndDate(memberNum, dateDto.getYear(), month);

        // 2단계: 조회되는 일기가 없다면 null 반환
        if (diaryVoList.isEmpty()) return null;

        // 3단계: 일기 별 태그 리스트 조회
        Map<Integer, List<DiaryTagVo>> memberDiaryTagMap = new HashMap<>();
        for (var diaryVo: diaryVoList) {
            int diaryNum = diaryVo.getDiaryNum();

            // 태그 리스트를 조회하고 DIARY_NUM을 key로 map에 삽입
            List<DiaryTagVo> diaryTags = diaryTagDao.selectByDiaryNum(diaryNum);
            if (!diaryTags.isEmpty()) memberDiaryTagMap.put(diaryNum, diaryTags);
        }

        // 최종: 반환용 리스트
        List<DiaryDto> diaryDtoList = new ArrayList<>();
        for (var diaryVo: diaryVoList) {
            DiaryDto diaryDto = new DiaryDto();
            diaryDto.setTitle(diaryVo.getTitle());
            diaryDto.setContent(diaryVo.getContent());

            List<String> tagNameList = Optional.ofNullable(memberDiaryTagMap.get(diaryVo.getDiaryNum()))
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(DiaryTagVo::getTagName)
                    .toList();
            diaryDto.setTags(tagNameList.isEmpty() ? null : tagNameList);
            diaryDto.setWrittenDate(TimeUtils.convertLocalDateTimeToString(diaryVo.getWrittenDate()));
            diaryDtoList.add(diaryDto);
        }

        return !diaryDtoList.isEmpty() ? diaryDtoList : null;
    }
}
