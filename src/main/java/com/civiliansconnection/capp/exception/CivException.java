package com.civiliansconnection.capp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CivException extends RuntimeException {

    public CivException(String message) {
        super(message);
    }
}
