package com.bits.oems.user.exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No such event found")
public class NoSuchEventFoundException extends RuntimeException {
    public NoSuchEventFoundException() {
        super("No such event found");
    }
}
