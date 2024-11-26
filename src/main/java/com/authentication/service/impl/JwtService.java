package com.authentication.service.impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private String secretKey;

	public JwtService() {
		super();
		this.secretKey = generateSecretKey();
	}

	private String generateSecretKey() {
		try {
			KeyGenerator keyGenrator = KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey = keyGenrator.generateKey();
			String encodedSecretKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
			System.out.println("generateSecretKey :: Secret Key = " + encodedSecretKey);
			return encodedSecretKey;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Error occured while generating key", e);
		}
	}
	
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		System.out.println("Decoded SecretKey =" + keyBytes.toString());
		return Keys.hmacShaKeyFor(keyBytes);
	}

	
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		
		JwtBuilder jwtBuilder = Jwts.builder().claims(claims);
		jwtBuilder.subject(userName);
		jwtBuilder.issuedAt(new Date(System.currentTimeMillis()));
		jwtBuilder.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30));
		jwtBuilder.signWith(getKey());
		return jwtBuilder.compact();
		
		
		
//		return Jwts.builder().claims(claims).subject(userName)
//				.issuedAt(new Date(System.currentTimeMillis()))
//				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
//				.signWith(getKey()).compact();
	}

	

	public String extractUserNameFromToken(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	private <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		JwtParserBuilder jwtParserBuilder = Jwts.parser().verifyWith((SecretKey) getKey());
		Jws<Claims> claims = jwtParserBuilder.build().parseSignedClaims(token);
		Claims payload = claims.getPayload();
		return payload;
		//return Jwts.parser().verifyWith((SecretKey) getKey()).build().parseSignedClaims(token).getPayload();
	}

	// This method will do validate the userName in token against the userName in
	// Security Context UserDetails object
	public boolean validateToken(String token, UserDetails userDetails) {
		String userName = extractUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpireation(token).before(new Date());
	}

	private Date extractExpireation(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
}
