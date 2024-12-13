package com.team3.camping.adminSocialAccountMngServiceTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.service.adminService.AdminSocialAccountMngService;

@SpringBootTest
class AdminSocialAccountMngServiceTest {

	@Autowired
	private AdminSocialAccountMngService adminSocialAccountMngService;
	
	@Test
	void getSocialAccountListTest() {
		
		PagingUtil objSocailAccountList = adminSocialAccountMngService.getSocialAccountList(null, null, null, null, 100, 1, 10);
		
		assertEquals(1, objSocailAccountList.getObjUserList().size());
	}
}
