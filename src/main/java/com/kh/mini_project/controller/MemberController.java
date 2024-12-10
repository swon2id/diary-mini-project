package com.kh.mini_project.controller;

import com.kh.mini_project.dao.MemberDao;
import com.kh.mini_project.dto.request.UpdateMemberRequest;
import com.kh.mini_project.vo.MemberVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/members")

public class MemberController {

    private final MemberDao memberDao;

    // 회원탈퇴 (POST)
    @PostMapping("/delete")
    public ResponseEntity<String> deleteMember(@RequestBody Map<String, String> request) {
        try {
            String id = request.get("id");
            String password = request.get("password");

            // 유효성 검사
            if (id == null || password == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디와 비밀번호를 모두 입력해주세요.");
            }

            // ID와 비밀번호 검증
            int validUserCount = memberDao.selectCountByIdAndPassword(id, password);
            if (validUserCount == 0) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 올바르지 않습니다.");
            }

            // 회원번호 조회 후 삭제
            int memberNum = memberDao.selectMemberNumById(id);
            memberDao.deleteMemberData(memberNum);

            return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    // 회원정보 수정
    @PostMapping("/update")
    public ResponseEntity<String> updateMemberInfo(@RequestBody UpdateMemberRequest dto) {
        String newId = dto.getId();
        String newPassword = dto.getPassword();
        String newEmail = dto.getEmail();
        String newNickname = dto.getNickname();
        try {
            // 업데이트 로직 호출
            memberDao.updateMemberInfo(newId, newEmail, newNickname, newPassword);
            return ResponseEntity.ok("회원정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 수정 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/get")
    public ResponseEntity<Object> getMemberInfo(@RequestBody Map<String, String> id) {
        try {
            // 업데이트 로직 호출
            Map<String, Object> response = new HashMap<>();
            response.put("memberInfo", memberDao.selectById(id.get("id")));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원정보 수정 중 오류가 발생했습니다.");
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity<String> deleteMember(@RequestBody Map<String, String> requestBody) {
//        String id = requestBody.get("id");
//        String password = requestBody.get("password");
//
//        try {
//            // ID와 비밀번호 검증
//            int count = memberDao.selectCountByIdAndPassword(id, password);
//            if (count == 0) {
//                return ResponseEntity.badRequest().body("아이디 또는 비밀번호가 일치하지 않습니다.");
//            }
//
//            // 회원 번호 조회
//            int memberNum = memberDao.selectMemberNumById(id);
//            if (memberNum == -1) {
//                return ResponseEntity.badRequest().body("회원 정보를 찾을 수 없습니다.");
//            }
//
//            // 참조 데이터 및 회원 데이터 삭제
//            memberDao.deleteMemberData(memberNum);
//            return ResponseEntity.ok("회원 및 관련 데이터가 성공적으로 삭제되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("회원 삭제 중 오류가 발생했습니다.");
//        }
//    }
}