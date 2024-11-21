package com.bits.oems.user.controller;

import javax.validation.Valid;

import com.bits.oems.commons.model.*;
import com.bits.oems.user.exception.EmailAlreadyExistsRuntimeException;
import com.bits.oems.user.exception.IncorrectPasswordRuntimeException;
import com.bits.oems.user.exception.IncorrectSecurityAnswerRuntimeException;
import com.bits.oems.user.exception.UsernameTakenRuntimeException;
import com.bits.oems.user.model.EmailLoginRequest;
import com.bits.oems.user.model.ForgotPasswordRequest;
import com.bits.oems.user.model.UsernameLoginRequest;
import com.bits.oems.user.service.AuthService;
import com.bits.oems.user.service.impl.JWTValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.oems.commons.exception.NoSuchUserRuntimeException;
import com.bits.oems.commons.logger.LogExecutionTime;
import com.bits.oems.commons.utility.AppUtility;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthService authService;

    private final JWTValidationService jwtValidationService;

    private final AppUtility utility;

    protected AuthController(AuthService authService, AppUtility utility,
                             JWTValidationService jwtValidationService) {
        this.authService = authService;
        this.jwtValidationService = jwtValidationService;
        this.utility = utility;
    }

    @PostMapping(value = "/register")
    @LogExecutionTime
    public ResponseEntity<Payload<TokenResponse>> registerUser(@RequestBody @Valid RegisterRequest request)
            throws UsernameTakenRuntimeException, EmailAlreadyExistsRuntimeException {
        return utility.buildSuccessEntity(HttpStatus.CREATED, authService.registerUser(request));
    }

    @PostMapping(value = "/login/username")
    @LogExecutionTime
    public ResponseEntity<Payload<TokenResponse>> loginWithUsername(@RequestBody @Valid UsernameLoginRequest request)
            throws IncorrectPasswordRuntimeException, NoSuchUserRuntimeException {
        return utility.buildSuccessEntity(authService.loginWithUsername(request));
    }

    @GetMapping(value = "/{username}")
    @LogExecutionTime
    public AuthUserDto checkUsername(@PathVariable("username") String username) {
        return authService.checkUsername(username);
    }

    @PostMapping(value = "/login/email")
    @LogExecutionTime
    public ResponseEntity<Payload<TokenResponse>> loginWithEmail(@RequestBody @Valid EmailLoginRequest request)
            throws IncorrectPasswordRuntimeException, NoSuchUserRuntimeException {
        return utility.buildSuccessEntity(authService.loginWithEmail(request));
    }

    @GetMapping(value = "/{username}/securityQn")
    @LogExecutionTime
    public ResponseEntity<Payload<String>> fetchSecurityQn(@PathVariable("username") String username)
            throws NoSuchUserRuntimeException {
        return utility.buildSuccessEntity(authService.fetchSecurityQn(username));
    }

    @PostMapping(value = "/{username}/forgot")
    @LogExecutionTime
    public ResponseEntity<Payload<Boolean>> forgotPassword(@PathVariable("username") String username,
                                                           @RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest)
            throws IncorrectSecurityAnswerRuntimeException, NoSuchUserRuntimeException {
        return utility.buildSuccessEntity(authService.forgotPassword(username, forgotPasswordRequest));
    }

    @PostMapping(value = "/validate")
    @LogExecutionTime
    public UsernameResponse validateToken(@Valid @RequestBody TokenValidationRequest request) {
        return jwtValidationService.validateToken(request.getToken());
    }

}
