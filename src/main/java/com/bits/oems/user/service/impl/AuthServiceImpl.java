package com.bits.oems.user.service.impl;

import java.util.HashSet;
import java.util.Optional;

import com.bits.oems.commons.model.AuthUserDto;
import com.bits.oems.user.exception.EmailAlreadyExistsRuntimeException;
import com.bits.oems.user.exception.IncorrectPasswordRuntimeException;
import com.bits.oems.user.exception.IncorrectSecurityAnswerRuntimeException;
import com.bits.oems.user.exception.UsernameTakenRuntimeException;
import com.bits.oems.user.model.ForgotPasswordRequest;
import com.bits.oems.user.model.User;
import com.bits.oems.user.repository.UserRepository;
import com.bits.oems.user.service.AuthService;
import com.bits.oems.user.utility.AppJwtUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.bits.oems.user.model.EmailLoginRequest;
import com.bits.oems.user.model.UsernameLoginRequest;
import com.bits.oems.commons.exception.NoSuchUserRuntimeException;
import com.bits.oems.commons.logger.LogExecutionTime;
import com.bits.oems.commons.model.RegisterRequest;
import com.bits.oems.commons.model.TokenResponse;
import com.bits.oems.commons.utility.AppUtility;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUtility appUtility;

    private final UserRepository userRepository;

    private final AppJwtUtility jwtUtility;

    @LogExecutionTime
    @Override
    public TokenResponse registerUser(RegisterRequest registerRequest)
            throws UsernameTakenRuntimeException, EmailAlreadyExistsRuntimeException {
        User existingUser = userRepository.findByUsernameOrEmail(registerRequest.getUsername(),
                registerRequest.getEmail());
        if (existingUser != null) {
            if (StringUtils.equals(registerRequest.getUsername(), existingUser.getUsername())) {
                throw new UsernameTakenRuntimeException();
            } else {
                throw new EmailAlreadyExistsRuntimeException();
            }
        }
        final String id = appUtility.generateStringId();
        User user = buildAuthUser(id, registerRequest);
        userRepository.save(user);

        return jwtUtility.buildTokenResponse(user.getUsername());
    }

    private User buildAuthUser(String id, RegisterRequest registerRequest) {
        User user = new User();
        user.setId(id);
        user.setUsername(registerRequest.getUsername());
        user.setPassword(appUtility.encodeString(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setSecurityQn(registerRequest.getSecurityQn());
        user.setSecurityAnswer(appUtility.encodeString(registerRequest.getSecurityAnswer().toLowerCase()));
        user.setRoles(new HashSet<>());
        user.setRegisteredEvents(new HashSet<>());
        user.setPaymentIds(new HashSet<>());
        return user;
    }

    @LogExecutionTime
    @Override
    public TokenResponse loginWithUsername(UsernameLoginRequest loginRequest)
            throws NoSuchUserRuntimeException, IncorrectPasswordRuntimeException {
        final User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(NoSuchUserRuntimeException::new);

        return checkPasswordMatches(loginRequest.getPassword(), user);
    }

    @LogExecutionTime
    @Override
    public String fetchSecurityQn(String username) throws NoSuchUserRuntimeException {
        return userRepository.findByUsername(username).orElseThrow(NoSuchUserRuntimeException::new)
                .getSecurityQn();
    }

    @LogExecutionTime
    @Override
    public boolean forgotPassword(String username, ForgotPasswordRequest forgotPasswordRequest)
            throws NoSuchUserRuntimeException, IncorrectSecurityAnswerRuntimeException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(NoSuchUserRuntimeException::new);
        if (!appUtility.bcryptMatches(forgotPasswordRequest.getSecurityAnswer().toLowerCase(),
                user.getSecurityAnswer())) {
            throw new IncorrectSecurityAnswerRuntimeException();
        }
        String newPassword = forgotPasswordRequest.getNewPassword();
        user.setPassword(appUtility.encodeString(newPassword));
        userRepository.save(user);
        return appUtility.bcryptMatches(newPassword, user.getPassword());
    }

    @Override
    public TokenResponse loginWithEmail(EmailLoginRequest request)
            throws IncorrectPasswordRuntimeException, NoSuchUserRuntimeException {
        User user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()))
                .orElseThrow(NoSuchUserRuntimeException::new);

        return checkPasswordMatches(request.getPassword(), user);
    }

    @Override
    public AuthUserDto checkUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        AuthUserDto authUserDto = new AuthUserDto();
        authUserDto.setUsername(user.getUsername());
        authUserDto.setPassword(user.getPassword());
        authUserDto.setEmail(user.getEmail());
        authUserDto.setSecurityQn(user.getSecurityQn());
        return authUserDto;
    }

    private TokenResponse checkPasswordMatches(String loginPassword, User user)
            throws IncorrectPasswordRuntimeException {
        if (!appUtility.bcryptMatches(loginPassword, user.getPassword())) {
            throw new IncorrectPasswordRuntimeException();
        }
        return jwtUtility.buildTokenResponse(user.getUsername());
    }

}
