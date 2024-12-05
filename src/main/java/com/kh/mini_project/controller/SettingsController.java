package com.kh.mini_project.controller;

import com.kh.mini_project.dao.SettingDao;
import com.kh.mini_project.dto.request.MemberNumRequest;
import com.kh.mini_project.dto.response.SettingsResponse;
import com.kh.mini_project.vo.SettingVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/settings")
public class SettingsController {

    private final SettingDao settingDao;

    @PostMapping("/get")
    public ResponseEntity<Map<String, Object>> getSettings(@RequestBody MemberNumRequest memberNumRequestdto) {
        int memberNum = memberNumRequestdto.getMemberNum();
        SettingVo settingVo = settingDao.getDiarySetting(memberNum);
        if (settingVo == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "회원 설정값을 찾을 수 없습니다.");
            return ResponseEntity.status(404).body(errorResponse);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("data", SettingsResponse.from(settingVo));
        return ResponseEntity.ok(response);
    }
}
