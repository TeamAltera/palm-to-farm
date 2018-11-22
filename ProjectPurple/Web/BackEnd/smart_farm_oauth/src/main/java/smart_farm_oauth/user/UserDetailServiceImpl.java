package smart_farm_oauth.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service("userDetailServiceImpl")
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

	private UserRepository userRepository;
	
	//데이터 베이스로 부터 유저정보를 불러오는 역할 수행
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.out.println(username);
		User user=userRepository.findOneByUsername(username);
		if(user==null) { //사용자가 존재하지 않는다면
			throw new UsernameNotFoundException("UsernameNotFound ["+username+"]");
		}
		
		//토큰을 발급받은 시간을 해당 멤버 테이블에 업데이트
		//user.setLatestLoginAt(Timestamp.valueOf(LocalDateTime.now()));
		userRepository.save(user);
		return CustomUserDetails.create(user); //사용자가 존재한다면
	}

}
