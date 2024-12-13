package com.team3.camping.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.userRepository.UserSocialAccountDao;

@Service
public class UserSocialAccountServiceImpl implements UserSocialAccountService {

	@Autowired
	private UserSocialAccountDao adminSocialAccountDao;
	
    // 소셜 정보 수정
	@Override
	public ResultInfo socialAccountUpdDate(UserInfoVo objUserInfo) {

		return adminSocialAccountDao.socialAccountUpdDate(objUserInfo);
	}
}
