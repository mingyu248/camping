package com.team3.camping.adminAccountMngDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.adminRepository.AdminAccountMngDao;

@SpringBootTest
class AdminAccountMngDaoTest {

	@Autowired
	private AdminAccountMngDao adminAccountMngDao;
	
	@Test
	void adminIdDuplicateCheck() {
		
		String strAdminId = "admin0010";
		
		ResultInfo objResultInfo = new ResultInfo();
		
		objResultInfo = adminAccountMngDao.adminIdDuplicateCheck(strAdminId);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void userAccountActivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo   = new UserInfoVo();
		
		objUserInfo.setUserId("user0002");
		objUserInfo.setAdminId("admin");
		
		objResultInfo = adminAccountMngDao.adminAccountActivate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void userAccountDeactivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo   = new UserInfoVo();
		
		objUserInfo.setUserId("user0002");
		objUserInfo.setAdminId("admin");
		
		objUserInfo.setReason("부적절한 용어 사용");
		
		objResultInfo = adminAccountMngDao.adminAccountDeactivate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	
	@Test
	void adminAccountUpdateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		UserInfoVo objUserInfo   = new UserInfoVo();
		
		objUserInfo.setUserId("admin0050");
		objUserInfo.setAdminId("admin");
		// objUserInfo.setUserName("필표");
		objUserInfo.setUserTelNum("01056789932");
		objUserInfo.setUserEmail("admin0050@admin.net");
		
		objResultInfo = adminAccountMngDao.adminAccountUpdate(objUserInfo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
		//assertEquals("zz", objResultInfo.getStrRetVal());
	}
	
	@Test
	void adminGetAdminListTest() {
		
		//objAdminList = adminAccountMngDao.getAdminList(intPage, intLimit);
		
		//assertEquals(5, objAdminList.size());
	}
	
	@Test
	void getAdminCountByStatusTest() {
		
		List<UserInfoVo> objStatusCountList = adminAccountMngDao.getAdminOrUserCountByStatus("ROLE_ADMIN");
		
		assertEquals("정상", objStatusCountList.get(0).getStateCode());
	}
	
	@Test
	void stopAdminAccountTest() {
		
		ResultInfo objResultInfo = adminAccountMngDao.stopAdminAccount("admin", "admin0024", "비활성화");
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
}
