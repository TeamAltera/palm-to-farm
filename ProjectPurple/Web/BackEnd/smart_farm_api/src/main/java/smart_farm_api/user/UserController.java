package smart_farm_api.user;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.validators.UserInfoDtoValidator;
import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.service.GetUserInfoServiceImpl;
import smart_farm_api.user.service.JoinSearchServiceImpl;
import smart_farm_api.user.service.JoinServiceImpl;
import smart_farm_api.user.service.SignoutServiceImpl;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	
	private SignoutServiceImpl signoutService;
	
	private JoinServiceImpl joinService;
	
	private JoinSearchServiceImpl joinSearchService;
	
	private GetUserInfoServiceImpl getUserInfoService;
	
	@InitBinder("userInfoDto")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new UserInfoDtoValidator());
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/signout
	 * 로그아웃시 토큰 만료
	 * </pre>
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "로그아웃, target:front")
	@PostMapping("/signout")
	public ResultDto signout(Model model) {
		/*model.addAttribute("request", request);*/
		return signoutService.execute(model);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/signup
	 * {pwd: string, email: string, firstName: string, secondName: string}
	 * -pwd: 패스워드
	 * -email: 가입하고자 하는 이메일
	 * -firstName: 성
	 * -secondName: 이름
	 * 회원가입 동작 수행 
	 * </pre>
	 * @param userInfo
	 * @param result
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "회원 가입, target:front")
	@PostMapping(value = "/signup")
	public ResultDto memberJoinAction(@Valid @RequestBody UserInfoDto userInfo, BindingResult result, Model model) {
		if(result.hasErrors()) { //폼데이터의 유효성 검증결과에 따른 ResultDTO생성
			return ResultDto.createInstance(false).setMsg("입력 형식에 맞지 않습니다.").setData(result.getAllErrors());
		}
		model.addAttribute("userInfo", userInfo);
		return joinService.execute(model);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/info
	 * JWT토큰으로 부터 사용자 정보 반환
	 * </pre>
	 * @return
	 */
	@ApiOperation(value = "사용자 정보 조회, target:front")
	@GetMapping(value="/info")
	public ResultDto getUserInfo() {
		return getUserInfoService.execute(null);
	}
	
	/**
	 * <pre>
	 * target: front
	 * http://localhost:9001/smart_plant/user/find/{email}
	 * 회원가입 페이지에서 아이디조회를 위해 사용, 많은 커넥션이 요구되어지므로 validation제외
	 * -email: 이메일(string type)
	 * </pre>
	 * @param email
	 * @param model
	 * @return
	 */
	@ApiOperation(value = "등록된 이메일 인지 조회, target:front")
	@GetMapping(value = "/find")
	public ResultDto userExist(@RequestParam("email") String email, Model model){
		model.addAttribute("emailInfo", email);
		return joinSearchService.execute(model);
	}
}
