package com.bits.oems.user.service.impl;

import com.bits.oems.user.utility.AppJwtUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bits.oems.commons.logger.LogExecutionTime;
import com.bits.oems.commons.model.UsernameResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
@Primary
public class JWTValidationService implements com.bits.oems.commons.service.JWTValidationService {

	@Autowired
	private AppJwtUtility jwtUtility;

	private final Logger log = LoggerFactory.getLogger(JWTValidationService.class);

	@Override
	@LogExecutionTime
	public UsernameResponse validateToken(String token) {
		final UsernameResponse response = new UsernameResponse();
		try {
			final String username = jwtUtility.getUsername(token);
			response.setUsername(username);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			response.setError(e.getMessage());
		}
		if (log.isDebugEnabled()) {
			log.debug("validateToken: {}", response);
		}
		return response;
	}

}
