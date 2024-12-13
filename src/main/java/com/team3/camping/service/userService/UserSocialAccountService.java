package com.team3.camping.service.userService;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;

public interface UserSocialAccountService {
	
	// 소셜 정보 수정
	public ResultInfo socialAccountUpdDate(UserInfoVo objUserInfo);
}
