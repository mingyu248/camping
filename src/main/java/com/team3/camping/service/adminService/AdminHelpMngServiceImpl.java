package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.adminRepository.AdminHelpMngDao;

@Service
public class AdminHelpMngServiceImpl implements AdminHelpMngService {

	@Autowired
	private AdminHelpMngDao adminHelpMngDao;
	
	// 문의 목록
	@Override
	public PagingUtil getHelpList(String strHelpTitle, String strUserId, String strStartDate, String strEndDate, Integer intStateCode
			                     ,int intPage, int intLimit) {

		PagingUtil objPaginUtil = new PagingUtil();
		
		objPaginUtil.setCurrentPage(intPage);
		objPaginUtil.setLimit(intLimit);
		
		try {
			List<HelpVo> objHelpList = adminHelpMngDao.getHelpList(strHelpTitle, strUserId, strStartDate, strEndDate, intStateCode, objPaginUtil.getStartRow(), objPaginUtil.getEndRow());
			int intHelpTotalCount = adminHelpMngDao.getHelpTotalCount(strHelpTitle, strUserId, strStartDate, strEndDate, intStateCode);
			
			objPaginUtil.setTotalCount(intHelpTotalCount);
			objPaginUtil.calculateTotalPages(intHelpTotalCount);
			objPaginUtil.setObjHelpList(objHelpList);
			
			objPaginUtil.setPageRange(objPaginUtil.getPageRange());
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return objPaginUtil;
	}

	// 문의 목록
	@Override
	public HelpVo getUserHelpDetail(int intHelpNo) {
		
		return adminHelpMngDao.getUserHelpDetail(intHelpNo);
	}

	// 문의 답변
	@Override
	public ResultInfo adminInsertHelpAnswer(HelpVo objHelpVo) {

		return adminHelpMngDao.adminInsertHelpAnswer(objHelpVo);
	}

	// 상태 별 수
	@Override
	public List<HelpVo> getHelpCountByStatus() {

		return adminHelpMngDao.getHelpCountByStatus();
	}

	// 문의 삭제
	@Override
	public ResultInfo adminDeleteUserHelp(int intHelpNo) {

		return adminHelpMngDao.adminDeleteUserHelp(intHelpNo);
	}
}