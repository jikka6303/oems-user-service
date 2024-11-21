package com.bits.oems.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Event already registered")
public class EventAlreadyRegisteredException extends RuntimeException {
}
