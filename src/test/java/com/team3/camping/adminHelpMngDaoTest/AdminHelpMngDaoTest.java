package com.team3.camping.adminHelpMngDaoTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.adminRepository.AdminHelpMngDao;

@SpringBootTest
class AdminHelpMngDaoTest {

	@Autowired
	private AdminHelpMngDao adminHelpMngDao;
	
	@Test
	void adminInsertHelpAnswer() {
		
		ResultInfo objResultInfo = new ResultInfo();
		HelpVo objHelpVo = new HelpVo();
		
		objHelpVo.setHelpNo(2024000002);
		objHelpVo.setAdminId("admin");
		objHelpVo.setAnswer("문의 답변 했다.");
		
		objResultInfo = adminHelpMngDao.adminInsertHelpAnswer(objHelpVo);
		
		assertEquals(0, objResultInfo.getIntRetVal());
	}
}