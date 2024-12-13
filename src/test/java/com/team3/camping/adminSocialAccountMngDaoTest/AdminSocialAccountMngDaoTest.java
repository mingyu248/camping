package com.team3.camping.adminSocialAccountMngDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.adminRepository.AdminSocialAccountMngDao;

@SpringBootTest
class AdminSocialAccountMngDaoTest {

	@Autowired
	private AdminSocialAccountMngDao adminSocialAccountMngDao;
	
	@Test
	void getSocialAccountListTest() {
		
		List<UserInfoVo> objSocailAccountList = adminSocialAccountMngDao.getSocialAccountList("ja", "naver", "22/11/11", "25/11/11", 100, 1, 10);
		
		assertEquals(6, objSocailAccountList.size());
	}
}
