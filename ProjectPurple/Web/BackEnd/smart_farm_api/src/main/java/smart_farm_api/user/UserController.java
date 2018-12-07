package smart_farm_api.user;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import smart_farm_api.common.ResultDto;
import smart_farm_api.common.validators.UserInfoDtoValidator;
import smart_farm_api.user.domain.UserInfoDto;
import smart_farm_api.user.service.GetUserInfoServiceImpl;
import smart_farm_api.user.service.JoinSearchServiceImpl;
import smart_farm_api.user.service.JoinServiceImpl;
import smart_farm_api.user.service.SignoutServiceImpl;
import smart_farm_api.user.utils.UserConstants;
import smart_farm_api.user.utils.UserDocs;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController implements UserDocs{
	
	private SignoutServiceImpl signoutService;
	
	private JoinServiceImpl joinService;
	
	private JoinSearchServiceImpl joinSearchService;
	
	private GetUserInfoServiceImpl getUserInfoService;
	
	@InitBinder("userInfoDto")
	protected void initUserInfoDTOBinder(WebDataBinder binder) {
		binder.setValidator(new UserInfoDtoValidator());
	}
	
	@PostMapping("/signout")
	public ResultDto signout() {
		return signoutService.execute(null);
	}
	
	@PostMapping
	public ResultDto create(@Valid @RequestBody UserInfoDto userInfoDto, BindingResult result) {
		if(result.hasErrors()) {
			return ResultDto.createInstance(false).setMsg(UserConstants.VALIDATION_ERROR);
		}
		return joinService.execute(userInfoDto);
	}
	
	@GetMapping
	public ResultDto getUserInfo() {
		return getUserInfoService.execute(null);
	}
	
	@GetMapping(value = "/find")
	public ResultDto isExist(@RequestParam("email") String email){
		return joinSearchService.execute(email);
	}
}
