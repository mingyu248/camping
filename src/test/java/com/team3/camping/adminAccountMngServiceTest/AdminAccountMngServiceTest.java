package com.team3.camping.adminAccountMngServiceTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.service.adminService.AdminAccountMngService;

@SpringBootTest
class AdminAccountMngServiceTest {

	@Autowired
	private AdminAccountMngService adminAccountMngService;
	
	@Test
	void createAdminAccountTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo = new UserInfoVo();
		
		objUserInfo.setAdminId("admin");
		objUserInfo.setUserId("admin0002");
		objUserInfo.setUserPwd("admin");
		objUserInfo.setUserName("조필표");
		objUserInfo.setUserEmail("admin@admin.com");
		
		objUserInfo.setUserTelNum("01043252234");
		
		objResultInfo = adminAccountMngService.createAdminAccount(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void adminAccountDeactivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo = new UserInfoVo();
		
		objUserInfo.setUserId("admin0005");
		objUserInfo.setAdminId("admin");
		objUserInfo.setReason("부절절한 용어 사용");
		
		objResultInfo = adminAccountMngService.adminAccountDeactivate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void adminAccountActivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo = new UserInfoVo();
		
		objUserInfo.setUserId("admin0005");
		objUserInfo.setAdminId("admin");
		
		objResultInfo = adminAccountMngService.adminAccountActivate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void adminAccountDeleteTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		String strAdminId = "admin0004";
		
		objResultInfo = adminAccountMngService.adminAccountDelete(strAdminId);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void userAccountDeleteTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		String strUserId = "user0002";
		
		objResultInfo = adminAccountMngService.userAccountDelete(strUserId);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void adminAccountUpdateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo   = new UserInfoVo();
		
		objUserInfo.setUserId("admin0050");
		objUserInfo.setAdminId("admin");
		objUserInfo.setUserPwd("admin0050");
		
		objResultInfo = adminAccountMngService.adminAccontUpdate(objUserInfo);
		
		assertEquals("SUCCESS", objResultInfo.getStrRetVal());
	}
	
	@Test
	void adminGetAdminListTest() {
		
		//List<UserInfoVo> objAdminList = adminAccountMngService.getAdminList(intPage, intLimit);
		
		//assertEquals(5, objAdminList.size());
	}
}
