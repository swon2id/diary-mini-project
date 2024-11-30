package com.kh.mini_project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * 잘못된 요청, 쿼리 실행 실패 등으로 인해 발생하는 모든 예외를 식별하고 클라이언트에 실패 응답을 반환합니다.
 * */

@Slf4j
// 스프링 부트 애플리케이션에서 RestController에 대해 전역적으로 예외를 핸들링할 수 있게 해주는 어노테이션
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * UNIQUE 제약조건 위반
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateKey(DuplicateKeyException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "UNIQUE 제약조건 위반입니다.");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * UNIQUE 이외 무결성 제약조건 위반
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "UNIQUE 이외 무결성 제약조건 위반입니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 형식의 값이 전달된 경우
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "잘못된 형식의 값이 전달되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 존재하지 않는 API를 요청한 경우
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFound(NoResourceFoundException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "해당 API는 존재하지 않습니다.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * 데이터 삽입 실패(insert 리턴 값이 0인 경우) 예외 처리
     */
    @ExceptionHandler(InsertFailureException.class)
    public ResponseEntity<Map<String, Object>> handleInsertFailure(InsertFailureException e) {
        log.error("INSERT 실패: {}", e.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "알 수 없는 데이터 삽입 오류입니다. 서버 측 로그를 확인하세요.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 나머지 모든 기타 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleInternalError(Exception e) {
        log.error("처리되지 않은 예외: {}", e.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("isSuccess", false);
        response.put("error", "알 수 없는 오류입니다. 서버 측 로그를 확인하세요.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
