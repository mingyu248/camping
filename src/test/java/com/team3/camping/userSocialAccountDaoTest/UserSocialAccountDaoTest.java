package com.team3.camping.userSocialAccountDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.userRepository.UserSocialAccountDao;

@SpringBootTest
class UserSocialAccountDaoTest {

	@Autowired
	private UserSocialAccountDao userSocialAccountDao;
	
	@Test
	void socialAccountUpdDateTest() {

		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo   = new UserInfoVo();
		
		objUserInfo.setUserEmail("jaguar11789@naver.com");
		objUserInfo.setGender("m");
		objUserInfo.setZipCode("12345");
		objUserInfo.setBaseAddr("서울시 관악구 신림동");
		objUserInfo.setDtlAddr("123-1234");
		
		objResultInfo = userSocialAccountDao.socialAccountUpdDate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
}
