package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.User;
import com.team3.camping.repository.adminRepository.AdminSocialAccountMngDao;

@Service
public class AdminSocialAccountMngServiceImpl implements AdminSocialAccountMngService {

	@Autowired
	private AdminSocialAccountMngDao adminSocialAccountMngDao;
	
	// 소셜 플랫폼 추출
	@Override
	public List<User> getSocialAccountPlatform() {
		
		return adminSocialAccountMngDao.getSocialAccountPlatform();
	}

	// 소셜 계정 목록
	@Override
	public PagingUtil getSocialAccountList(String strUserEmailOrId, String strPlatform, String strStartDate, String strEndDate, Integer intStateCode
			                              ,int intPage, int intLimit) {
		
		PagingUtil objPagingUtil = new PagingUtil();
		
		objPagingUtil.setCurrentPage(intPage);
		objPagingUtil.setLimit(intLimit);
		
		try {
			List<UserInfoVo> objSocialAccountList = adminSocialAccountMngDao.getSocialAccountList(strUserEmailOrId, strPlatform, strStartDate, strEndDate, intStateCode
					                                                                             ,objPagingUtil.getStartRow(), objPagingUtil.getEndRow());
			int intSocialAccountTotalCount = adminSocialAccountMngDao.getSocialAccountTotalCount(strUserEmailOrId, strPlatform, strStartDate, strEndDate, intStateCode);
			
			objPagingUtil.setTotalCount(intSocialAccountTotalCount);
			objPagingUtil.calculateTotalPages(intSocialAccountTotalCount);
			objPagingUtil.setObjUserList(objSocialAccountList);
			
			objPagingUtil.setPageRange(objPagingUtil.getPageRange());
			
		} catch (Exception ex) {
			
		}
		return objPagingUtil;
	}

	// 소셜 회원 상세정보
	@Override
	public UserInfoVo getSocialAccountDetail(int intUserNo) {
		
		return adminSocialAccountMngDao.getSocialAccountDetail(intUserNo);
	}

	// 소셜 회원 상세내역
	@Override
	public List<UserInfoVo> getSocialAccountHistory(int intUserNo) {

		return adminSocialAccountMngDao.getSocialAccountHistory(intUserNo);
	}

	// 소셜 회원 정지
	@Override
	public ResultInfo stopSocialAccount(UserInfoVo objUserInfo) {

		return adminSocialAccountMngDao.stopSocialAccount(objUserInfo);
	}

	// 소셜 계정 활성화
	@Override
	public ResultInfo activateSocialAccount(UserInfoVo objUserInfo) {

		return adminSocialAccountMngDao.activateSocialAccount(objUserInfo);
	}

	// 소셜 계정 삭제
	@Override
	public ResultInfo deleteSocialAccount(UserInfoVo objUserInfo) {
		
		return adminSocialAccountMngDao.deleteSocialAccount(objUserInfo);
	}

	// 소셜 상태별 개수
	@Override
	public List<UserInfoVo> getSocialCountByStatus() {

		return adminSocialAccountMngDao.getSocialCountByStatus();
	}
}
