package com.bits.oems.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Event not paid")
public class EventNotPaidException extends RuntimeException {
}
