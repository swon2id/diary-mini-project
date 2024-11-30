package com.kh.mini_project.exception;

/**
 * 설명: 현재 구조에서 모든 실패 상황에 대한 로직 처리는 GlobalExceptionHandler를 통해 처리하고 있음.
 *       이 구조를 활용하기 위해 생성된 커스텀 예외
 *
 * 사용 시나리오 : INSERT 결과가 0인 경우 throw 하여 해당 SQL 구문과 파라미터를 로깅하고 및 클라이언트에 실패 응답 처리
 * */
public class InsertFailureException extends RuntimeException {
    public InsertFailureException(String message) {
        super(message);
    }
}