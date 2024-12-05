package com.kh.mini_project.exception;

import com.kh.mini_project.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

// RestAPI 형식으로 수신/응답하는 과정에서 발생하는 예외를 전역으로 핸들링할 수 있게 해주는 어노테이션
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 데이터베이스의 UNIQUE 제약조건 위반 시 발생하는 예외를 처리합니다.
     *
     * @param e 처리할 DuplicateKeyException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException e) {
        return new ResponseEntity<>(new ErrorResponse("UNIQUE 제약조건 위반입니다."), HttpStatus.CONFLICT);
    }

    /**
     * 데이터베이스의 무결성 제약조건(UNIQUE 이외) 위반 시 발생하는 예외를 처리합니다.
     *
     * @param e 처리할 DataIntegrityViolationException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(new ErrorResponse("UNIQUE 이외 무결성 제약조건 위반입니다."), HttpStatus.BAD_REQUEST);
    }

    /**
     * 데이터베이스 액세스 중 발생하는 일반적인 예외를 처리합니다.
     *
     * @param e 처리할 DataAccessException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(DataAccessException e) {
        log.error("처리되지 않은 DataAccessException: ", e);
        return new ResponseEntity<>(new ErrorResponse("데이터를 CRUD하는 과정에서 처리되지 않은 예외가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 전달 받은 데이터를 DTO에 매핑하는 과정에서 유효성 검증을 통과하지 못할 때 발생하는 예외를 처리합니다.
     *
     * @param e 처리할 MethodArgumentNotValidException 객체
     * @return 필드별 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> fieldErrorDetails = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                fieldErrorDetails.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(new ErrorResponse("유효하지 않은 요청입니다.", fieldErrorDetails), HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 본문이 없거나, 잘못된 JSON 형식으로 인해 요청을 읽을 수 없는 경우의 예외를 처리합니다.
     *
     * @param e 처리할 HttpMessageNotReadableException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ResponseEntity<>(new ErrorResponse("요청 본문이 없거나, 잘못된 JSON 형식입니다."), HttpStatus.BAD_REQUEST);
    }

    /**
     * 존재하지 않는 API 엔드포인트로 요청이 들어왔을 때의 예외를 처리합니다.
     *
     * @param e 처리할 NoResourceFoundException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        return new ResponseEntity<>(new ErrorResponse("해당 API 엔드포인트는 존재하지 않습니다."), HttpStatus.NOT_FOUND);
    }

    /**
     * 설정되지 않은 HTTP 메서드로 요청이 들어왔을 때의 예외를 처리합니다.
     *
     * @param e 처리할 HttpRequestMethodNotSupportedException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedRequestMethod(HttpRequestMethodNotSupportedException e) {
        return new ResponseEntity<>(new ErrorResponse("해당 HTTP 메서드는 지원하지 않습니다."), HttpStatus.NOT_FOUND);
    }

    /**
     * 그 외에 처리되지 않은 모든 예외를 처리합니다.
     *
     * @param e 처리할 Exception 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity 객체
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalError(Exception e) {
        log.error("처리되지 않은 Exception: ", e);
        return new ResponseEntity<>(new ErrorResponse("처리되지 않은 예외가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
