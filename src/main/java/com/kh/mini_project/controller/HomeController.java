package com.kh.mini_project.controller;

import com.kh.mini_project.dao.DiaryDao;
import com.kh.mini_project.vo.DiaryVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/home")
@Slf4j
public class HomeController {
    private final DiaryDao diaryDao;

    // 해당 회원이 일기 전체 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<DiaryVo>> diaryListByMember(@PathVariable String userId) {
        List<DiaryVo> list = diaryDao.diaryListByMember(userId);
        return ResponseEntity.ok(list);
    }
}