package smart_farm_api.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import smart_farm_api.user.domain.LoginDto;
import smart_farm_api.user.domain.UserInfoDto;

@Mapper
public interface UserMapper {
	void incrementSfCount(@Param("count") int count, @Param("userCode") int userCode);
	
	void decrementSfCount(@Param("count") int count, @Param("userCode") int userCode);
	
	UserInfoDto searchMember(LoginDto dto);
	
	Object incrementBlockCount(String email);
	
	void initBlockCount(int userCode);
	
	UserInfoDto getMemberInfo(int userCode);
	
	void insertMember(UserInfoDto dto);
	
	int searchEmail(String email);
	
	int searchBlock(String email);
}
