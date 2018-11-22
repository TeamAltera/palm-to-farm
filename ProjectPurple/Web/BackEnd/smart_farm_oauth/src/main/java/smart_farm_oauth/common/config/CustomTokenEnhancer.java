package smart_farm_oauth.common.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import smart_farm_oauth.user.CustomUserDetails;

//토큰에 값을 포함시키기 위함
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, 
      OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        
        //userdetails를 얻어옴
        CustomUserDetails details=(CustomUserDetails)authentication.getUserAuthentication().getPrincipal();
        
        //user에서 key값 추출
        additionalInfo.put(
          "key", details.getUser().getId());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(
          additionalInfo);
        return accessToken;
    }
}