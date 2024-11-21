package com.bits.oems.user.exception;

import com.bits.oems.commons.constant.AppConstants;
import com.bits.oems.commons.exception.AppRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = AppConstants.EMAIL_ALREADY_EXISTS)
public class EmailAlreadyExistsRuntimeException extends AppRuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
