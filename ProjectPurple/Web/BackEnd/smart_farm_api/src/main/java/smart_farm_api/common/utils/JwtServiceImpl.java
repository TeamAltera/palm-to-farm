package smart_farm_api.common.utils;

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

	@SuppressWarnings("unchecked")
	public Integer get() {
		
		//request를 가져옴
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		
		//Authorization헤더에서 Bearer를 제외한 값 추출 
		String accessToken = request.getHeader("Authorization").split(" ")[1];
		
		//토큰 값 디코딩
		Jwt jwt=JwtHelper.decode(accessToken);
		String claims=jwt.getClaims();
		HashMap<String, Object> claimsMap = null;
		try {
			claimsMap = new ObjectMapper().readValue(claims, HashMap.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//토큰으로부터 key값 반환
		return Integer.parseInt(claimsMap.get("key").toString());
	}
}
