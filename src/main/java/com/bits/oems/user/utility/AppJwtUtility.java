package com.bits.oems.user.utility;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bits.oems.commons.logger.LogExecutionTime;
import com.bits.oems.commons.model.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class AppJwtUtility {

	private final Logger log = LoggerFactory.getLogger(AppJwtUtility.class);

	@Value("${auth.jwtExpirationInMs}")
	private long jwtExpirationInMs;

	@Value("${auth.jwtSecret}")
	private String jwtSecret;

	private static final Charset defaultCharset = StandardCharsets.UTF_8;

	@LogExecutionTime
	public String generateToken(String username) {
		final String subject = Base64.getEncoder().encodeToString(username.getBytes(defaultCharset));
		if (log.isDebugEnabled()) {
			log.debug("generateToken: for {}", subject);
		}
		return Jwts.builder().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();

	}

	@LogExecutionTime
	public String getUsername(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
			SignatureException, IllegalArgumentException {
		final Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		if (log.isDebugEnabled()) {
			log.debug("getUsername: claims -> {}", claims);
		}
		final String encUsername = claims.getSubject();
		final byte[] decoded = Base64.getDecoder().decode(encUsername);
		return new String(decoded, StandardCharsets.UTF_8);
	}

	@LogExecutionTime
	public TokenResponse buildTokenResponse(String username) {
		return new TokenResponse().token(generateToken(username));
	}
	
	

}
