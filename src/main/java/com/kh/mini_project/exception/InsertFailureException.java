package com.kh.mini_project.exception;

public class InsertFailureException extends RuntimeException {
    public InsertFailureException(String message) {
        super(message);
    }
}