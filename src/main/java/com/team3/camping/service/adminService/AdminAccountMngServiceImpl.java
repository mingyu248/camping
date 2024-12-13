package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.adminRepository.AdminAccountMngDao;

@Service
public class AdminAccountMngServiceImpl implements AdminAccountMngService {

	@Autowired
	private AdminAccountMngDao adminAccountMngDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// 관리자 계정 중복확인
	@Override
	public ResultInfo adminIdDuplicateCheck(String strAdminId) {

		return adminAccountMngDao.adminIdDuplicateCheck(strAdminId);
	}

	// 관리자 계정생성
	@Override
	public ResultInfo createAdminAccount(UserInfoVo objUserInfo) {

		ResultInfo objResultInfo = new ResultInfo();

		try {
			objUserInfo.setUserPwd(passwordEncoder.encode(objUserInfo.getUserPwd()));

			objResultInfo = adminAccountMngDao.createAdminAccount(objUserInfo);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("===========[AdminAccountMngService][createAdminAccount]=========== Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 계정 비활성화
	@Override
	public ResultInfo adminAccountDeactivate(UserInfoVo objUserInfo) {

		return adminAccountMngDao.adminAccountDeactivate(objUserInfo);
	}

	// 관리자 계정 활성화
	@Override
	public ResultInfo adminAccountActivate(UserInfoVo objUserInfo) {

		return adminAccountMngDao.adminAccountActivate(objUserInfo);
	}

	// 관리자 계정 삭제
	@Override
	public ResultInfo adminAccountDelete(String strAdminId) {

		return adminAccountMngDao.adminAccountDelete(strAdminId);
	}

	// 회원 계정 삭제
	@Override
	public ResultInfo userAccountDelete(String strUserId) {

		return adminAccountMngDao.userAccountDelete(strUserId);
	}

	// 관리자 계정 수정
	@Override
	public ResultInfo adminAccontUpdate(UserInfoVo objUserInfo) {

		ResultInfo objResultInfo = new ResultInfo();

		try {
			objUserInfo.setUserPwd(passwordEncoder.encode(objUserInfo.getUserPwd()));

			objResultInfo = adminAccountMngDao.adminAccountUpdate(objUserInfo);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("======================================[AdminAccountMngService][createAdminAccount]====================================== Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 목록
	@Override
	public PagingUtil getAdminOrUserList(String strAdminId, String strStartDate, String strEndDate, Integer intStateCode, int intPage, int intLimit, String strRole) {

	    PagingUtil objPagingUtil = new PagingUtil();
	    
	    objPagingUtil.setCurrentPage(intPage);
	    objPagingUtil.setLimit(intLimit);

	    try {
	        List<UserInfoVo> objAdminList = adminAccountMngDao.getAdminOrUserList(strAdminId, strStartDate, strEndDate, intStateCode, objPagingUtil.getStartRow(), objPagingUtil.getEndRow(), strRole);
	        int intAdminTotalCount        = adminAccountMngDao.getAdminOrUserTotalCount(strAdminId, strStartDate, strEndDate, intStateCode, strRole);
	        
	        objPagingUtil.setTotalCount(intAdminTotalCount);
	        objPagingUtil.calculateTotalPages(intAdminTotalCount);
	        objPagingUtil.setObjUserList(objAdminList);

	        objPagingUtil.setPageRange(objPagingUtil.getPageRange());
	        
	    } catch (Exception ex) {
	        // TODO: handle exception
	    }
	    return objPagingUtil;
	}

	// 관리자 상태 별 수
	@Override
	public List<UserInfoVo> getAdminOrUserCountByStatus(String strRole) {
		
		return adminAccountMngDao.getAdminOrUserCountByStatus(strRole);
	}

	// 관리자 상세정보
	@Override
	public UserInfoVo getAdminOrUserDetailInfo(int intAdminNo, String strRole) {
		
		return adminAccountMngDao.getAdminOrUserDetailInfo(intAdminNo, strRole);
	}

	// 관리자 상세내역
	@Override
	public List<UserInfoVo> getAdminOrUserHistory(int intAdminNo) {
		
		return adminAccountMngDao.getAdminOrUserHistory(intAdminNo);
	}
	
	// 관리자 계정 비활성화
	@Override
	public ResultInfo stopAdminAccount(String strAdminId, String strStopAdminId, String strReason) {
		
		return adminAccountMngDao.stopAdminAccount(strAdminId, strStopAdminId, strReason);
	}

	// 관리자 계정 활성화
	@Override
	public ResultInfo activateAdminAccount(String strAdminId, String strActAdminId) {

		return adminAccountMngDao.activateAdminAccount(strAdminId, strActAdminId);
	}

	// 관리자 계정 삭제
	@Override
	public ResultInfo deleteAdminAccount(String strDelAdminId) {
		
		return adminAccountMngDao.deleteAdminAccount(strDelAdminId);
	}

	// 관리자 정보 수정
	@Override
	public ResultInfo updateAdminAccount(UserInfoVo objAdminInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			objAdminInfo.setUserPwd(passwordEncoder.encode(objAdminInfo.getUserPwd()));
			
			objResultInfo = adminAccountMngDao.updateAdminAccount(objAdminInfo);
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("======================================[AdminAccountMngService][createAdminAccount]====================================== Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
}