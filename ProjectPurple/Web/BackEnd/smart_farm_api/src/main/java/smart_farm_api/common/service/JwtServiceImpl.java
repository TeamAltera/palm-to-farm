package smart_farm_api.common.service;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JwtServiceImpl{
	private static final String SALT = "luvookSecret";

	@SuppressWarnings("unchecked")
	public Integer get() {// JWT에 있는 데이터를 가져오는 코드
		// TODO Auto-generated method stub
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String accessToken = request.getHeader("Authorization").split(" ")[1];
		Jwt jwt=JwtHelper.decode(accessToken);
		String claims=jwt.getClaims();
		HashMap<String, Object> claimsMap = null;
		try {
			claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.parseInt(claimsMap.get("key").toString());
	}
}
