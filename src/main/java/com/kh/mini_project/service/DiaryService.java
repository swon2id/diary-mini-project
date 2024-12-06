package com.kh.mini_project.service;

import com.kh.mini_project.common.TimeUtils;
import com.kh.mini_project.dao.*;
import com.kh.mini_project.dto.DiaryDto;
import com.kh.mini_project.dto.MonthlyDiaryEntryDto;
import com.kh.mini_project.dto.request.AuthenticateLoginRequest;
import com.kh.mini_project.dto.request.GetMonthlyDiaryListRequest;
import com.kh.mini_project.vo.CodingDiaryEntryVo;
import com.kh.mini_project.vo.CodingDiaryVo;
import com.kh.mini_project.vo.DiaryTagVo;
import com.kh.mini_project.vo.DiaryVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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
    public void saveNewDiary(AuthenticateLoginRequest loginDto, DiaryDto diaryDto) {
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

        LinkedHashSet<String> diaryTags = diaryDto.getTags();
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

    public List<MonthlyDiaryEntryDto> getDiaryListMonthly(AuthenticateLoginRequest loginDto, GetMonthlyDiaryListRequest.DateDto dateDto) {
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
        List<MonthlyDiaryEntryDto> monthlyDiaryEntryList = new ArrayList<>();
        for (var diaryVo: diaryVoList) {
            MonthlyDiaryEntryDto monthlyDiaryEntry = new MonthlyDiaryEntryDto();
            monthlyDiaryEntry.setDiaryNum(diaryVo.getDiaryNum().toString());
            monthlyDiaryEntry.setTitle(diaryVo.getTitle());
            monthlyDiaryEntry.setContent(diaryVo.getContent());
            monthlyDiaryEntry.setTags(
                    Optional.ofNullable(memberDiaryTagMap.get(diaryVo.getDiaryNum()))
                            .orElse(Collections.emptyList())
                            .stream()
                            .map(DiaryTagVo::getTagName)
                            .collect(Collectors.collectingAndThen(
                                    Collectors.toCollection(LinkedHashSet::new),
                                    tags -> tags.isEmpty() ? null : tags
                            ))
            );
            monthlyDiaryEntry.setWrittenDate(TimeUtils.convertLocalDateTimeToString(diaryVo.getWrittenDate()));
            monthlyDiaryEntryList.add(monthlyDiaryEntry);
        }

        return !monthlyDiaryEntryList.isEmpty() ? monthlyDiaryEntryList : null;
    }

    public DiaryDto getDiary(AuthenticateLoginRequest loginDto, int diaryNum) {
        int memberNum = memberDao.selectMemberNumById(loginDto.getId());
        if (memberNum == -1) {
            throw new IllegalArgumentException("ID, PW는 인증되었지만, ID로 memberNum을 조회하는데 실패하였습니다.");
        }

        // 1단계: 일기 조회
        DiaryVo diaryVo = diaryDao.selectByDiaryNum(diaryNum);

        // 2단계: 조회되는 일기가 없다면 null 반환
        if (diaryVo == null) return null;

        // 3단계: 일기의 태그 리스트 조회
        List<DiaryTagVo> diaryTagVoList = diaryTagDao.selectByDiaryNum(diaryNum);

        // 4단계: 일기에 대한 코딩 일기 및 코딩 일기 항목 조회
        CodingDiaryVo codingDiaryVo = codingDiaryDao.selectByDiaryNum(diaryNum);
        List<DiaryDto.CodingDiaryEntryDto> codingDiaryEntries = null;
        if (codingDiaryVo != null) {
            List<CodingDiaryEntryVo> codingDiaryEntryVoList = codingDiaryEntryDao.selectByCodingDiaryNum(codingDiaryVo.getCodingDiaryNum());
            codingDiaryEntries = new ArrayList<>();

            for (var entryVo: codingDiaryEntryVoList) {
                DiaryDto.CodingDiaryEntryDto entryDto = new DiaryDto.CodingDiaryEntryDto();
                entryDto.setProgrammingLanguageName(entryVo.getProgrammingLanguageName());
                entryDto.setContent(entryVo.getContent());
                entryDto.setSequence(entryVo.getSequence());
                codingDiaryEntries.add(entryDto);
            }
        }

        // 5단계: 응답을 위한 DiaryDto 인스턴스 생성
        DiaryDto diaryDto = new DiaryDto();
        diaryDto.setTitle(diaryVo.getTitle());
        diaryDto.setContent(diaryVo.getContent());
        diaryDto.setTags(diaryTagVoList.isEmpty()
                ? null
                : diaryTagVoList.stream()
                .map(DiaryTagVo::getTagName)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        diaryDto.setWrittenDate(TimeUtils.convertLocalDateTimeToString(diaryVo.getWrittenDate()));
        diaryDto.setCodingDiaryEntries(codingDiaryEntries);

        return diaryDto;
    }
}
