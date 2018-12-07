package smart_farm_api.user.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.domain.UserInfoVo;

@Mapper
public interface UserMapper {
	
	//사용자의 재배기 개수 증가
	void incrementSfCount(@Param("count") int count, @Param("userCode") int userCode);
	
	//사용자의 재배기 개수 감소
	void decrementSfCount(@Param("count") int count, @Param("userCode") int userCode);
	
	Object incrementBlockCount(String email);
	
	void initBlockCount(int userCode);
	
	//사용자 정보 반환
	UserInfoVo getMember(int userCode);
	
	void insertMember(UserInfoDto dto);
	
	int searchEmail(String email);
	
	int searchBlock(String email);
}
