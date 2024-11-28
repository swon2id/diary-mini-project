package com.kh.mini_project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@Slf4j
// 스프링 부트 애플리케이션에서 전역적으로 예외를 핸들링할 수 있게 해주는 어노테이션
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 데이터 삽입 무결성 예외 처리
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Boolean>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error("무결성 제약조건 위반: {}", e.getCause().getMessage());

        HttpStatus code = switch (e.getCause().getMessage()) {
            // ID 중복
            // 이메일 중복
            // 닉네임 중복
            case "SYS_C007380", "SYS_C007381", "SYS_C007382" -> HttpStatus.CONFLICT;
            // 기타 무결성 제약조건 위반
            default -> HttpStatus.BAD_REQUEST;
        };

        Map<String, Boolean> response = new HashMap<>();
        response.put("isSuccess", false);
        return new ResponseEntity<>(response, code);
    }

    /**
     * 데이터 삽입 실패(insert 리턴 값이 0인 경우) 예외 처리
     */
    @ExceptionHandler(InsertFailureException.class)
    public ResponseEntity<Map<String, Boolean>> handleInsertFailureException(InsertFailureException e) {
        log.error("INSERT 실패: {}", e.getMessage());

        Map<String, Boolean> response = new HashMap<>();
        response.put("isSuccess", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 나머지 모든 기타 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Boolean>> handleInternalError(Exception e) {
        log.error("처리되지 않은 예외: {}", e.getMessage());

        Map<String, Boolean> response = new HashMap<>();
        response.put("isSuccess", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
