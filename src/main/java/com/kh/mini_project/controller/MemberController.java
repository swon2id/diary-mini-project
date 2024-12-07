package com.kh.mini_project.controller;

import com.kh.mini_project.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")

public class MemberController {

    private final MemberDao memberDao;

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMember(@RequestBody Map<String, String> requestBody) {
        String id = requestBody.get("id");
        String password = requestBody.get("password");

        try {
            // ID와 비밀번호 검증
            int count = memberDao.selectCountByIdAndPassword(id, password);
            if (count == 0) {
                return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 일치하지 않습니다.");
            }

            // 회원 번호 조회
            int memberNum = memberDao.selectMemberNumById(id);
            if (memberNum == -1) {
                return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
            }

            // 참조 데이터 및 회원 데이터 삭제
            memberDao.deleteMemberData(memberNum);
            return ResponseEntity.ok("회원 및 관련 데이터가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("회원 삭제 중 오류가 발생했습니다.");
        }
    }
}