package com.theater.kobrin.exception;

import org.springframework.http.HttpStatus;

public class NotValidFieldException extends BaseException {
    public NotValidFieldException(String message) {
        super(HttpStatus.valueOf(422), message);
    }
}

