package com.team3.camping.adminCampingMngDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.adminRepository.AdminCampMngDao;

@SpringBootTest
class AdminCampingMngDaoTest {

	@Autowired
	private AdminCampMngDao adminCampingMngDao;
	
	@Test
	void campInfoStatusDeactivateTest() {
		
		int intCampNo = 2024000001;
		ResultInfo objResultInfo = new ResultInfo();
		
		objResultInfo = adminCampingMngDao.campInfoStatusDeactivate(intCampNo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
	@Test
	void campInfoStatusActivate() {
		int intCampNo = 2024000001;
		ResultInfo objResultInfo = new ResultInfo();
		
		objResultInfo = adminCampingMngDao.campInfoStatusActivate(intCampNo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
}
