package com.spring.smart_plant.common.service.jwt;

import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("jwtService")
public class JwtServiceImpl implements JwtService {
	private static final String SALT = "luvookSecret";

	@Override
	public <T> String create(String key, T data, String subject) {// JWT생성
		// TODO Auto-generated method stub
		String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
				.setSubject(subject).claim(key, data).signWith(SignatureAlgorithm.HS256, makeKey()).compact(); // Serialization;
		return jwt;
	}

	private byte[] makeKey() {
		byte[] key = null;
		try {
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;
	}

	@Override
	public boolean isUsable(String jwt) {// 사용가능한지
		// TODO Auto-generated method stub
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(makeKey()).parseClaimsJws(jwt);
			return true;

		} catch (Exception e) { // claim으로 변환도중 예외 발생 시 유효하지 않은 토큰이라면 예외 발생
			throw new UnauthorizedException();
		}
	}

	public class UnauthorizedException extends RuntimeException {
		private static final long serialVersionUID = -2238030302650813813L;

		public UnauthorizedException() {
			super("계정 권한이 유효하지 않습니다.\n다시 로그인을 해주세요.");
		}
	}

	@Override
	public Map<String, Object> get(String key) {// JWT에 있는 데이터를 가져오는 코드
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String jwt = request.getHeader("Authorization");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
			throw new UnauthorizedException();
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (LinkedHashMap<String, Object>) claims.getBody().get(key);
		return value;
	}
}
