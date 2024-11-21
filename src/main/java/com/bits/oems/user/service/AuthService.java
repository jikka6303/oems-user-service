package com.bits.oems.user.service;

import com.bits.oems.commons.model.AuthUserDto;
import com.bits.oems.user.exception.EmailAlreadyExistsRuntimeException;
import com.bits.oems.user.exception.IncorrectPasswordRuntimeException;
import com.bits.oems.user.exception.IncorrectSecurityAnswerRuntimeException;
import com.bits.oems.user.exception.UsernameTakenRuntimeException;
import com.bits.oems.user.model.ForgotPasswordRequest;
import com.bits.oems.user.model.EmailLoginRequest;
import com.bits.oems.user.model.UsernameLoginRequest;
import com.bits.oems.commons.exception.NoSuchUserRuntimeException;
import com.bits.oems.commons.model.RegisterRequest;
import com.bits.oems.commons.model.TokenResponse;

public interface AuthService {

	TokenResponse registerUser(RegisterRequest registerRequest)
			throws UsernameTakenRuntimeException, EmailAlreadyExistsRuntimeException;

	TokenResponse loginWithUsername(UsernameLoginRequest loginRequest)
			throws NoSuchUserRuntimeException, IncorrectPasswordRuntimeException;

	String fetchSecurityQn(String username) throws NoSuchUserRuntimeException;

	boolean forgotPassword(String username, ForgotPasswordRequest forgotPasswordRequest)
			throws NoSuchUserRuntimeException, IncorrectSecurityAnswerRuntimeException;

	TokenResponse loginWithEmail(EmailLoginRequest request)
			throws IncorrectPasswordRuntimeException, NoSuchUserRuntimeException;

    AuthUserDto checkUsername(String username);
}
