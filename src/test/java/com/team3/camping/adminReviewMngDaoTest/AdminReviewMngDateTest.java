package com.team3.camping.adminReviewMngDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.repository.adminRepository.AdminReviewMngDao;

@SpringBootTest
class AdminReviewMngDateTest {

	@Autowired
	private AdminReviewMngDao adminReviewMngDao;
	
	@Test
	void userReviewDeactivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		ReviewVo   objReviewVo   = new ReviewVo();
		
		objReviewVo.setReviewNo(2024000007);
		objReviewVo.setAdminId("admin");
		objReviewVo.setReason("부적절한 단어 사용");
		
		objResultInfo = adminReviewMngDao.userCampReviewDeactivate(objReviewVo);
		
		assertEquals("SUCCESS", objResultInfo.getStrRetVal());
	}
	
	@Test
	void userReviewActivateTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		ReviewVo   objReviewVo   = new ReviewVo();
		
		objReviewVo.setReviewNo(2024000007);
		objReviewVo.setAdminId("admin");
		
		objResultInfo = adminReviewMngDao.userReviewActivate(objReviewVo);
		
		assertEquals("SUCCESS", objResultInfo.getStrRetVal());
	}
	
	@Test
	void userReviewDeleteTest() {
		
		ResultInfo objResultInfo = new ResultInfo();
		ReviewVo objReviewVo = new ReviewVo();
		objReviewVo.setReviewNo(2024000012);
		
		objResultInfo = adminReviewMngDao.userReviewDelete(objReviewVo);
		
		assertEquals("SUCCESS", objResultInfo.getStrRetVal());
	}
}
