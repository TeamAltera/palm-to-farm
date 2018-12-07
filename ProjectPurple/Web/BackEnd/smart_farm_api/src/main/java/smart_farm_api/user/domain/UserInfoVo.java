package smart_farm_api.user.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoVo {
	
	private int userCode;
	private String email;
	private String firstName;
	private String secondName;
	private int sfCnt;
	
}