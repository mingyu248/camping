package com.team3.camping.repository.adminRepository;

import java.util.List;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;

public interface AdminAccountMngDao {

	// 관리자 아이디 중복확인
	public ResultInfo adminIdDuplicateCheck(String strAdminId);
	// 관리자 계정생성
	public ResultInfo createAdminAccount(UserInfoVo objUserInfo);
	// 관리자 계정 비활성화
	public ResultInfo adminAccountDeactivate(UserInfoVo objUserInfo);
	// 관리자 계정 활성화
	public ResultInfo adminAccountActivate(UserInfoVo objUserInfo);
	// 관리자 계정 삭제
	public ResultInfo adminAccountDelete(String strAdminId);
	
	
	// 회원 계정 삭제
	public ResultInfo userAccountDelete(String strUserId);
	// 관리자 계정 수정
	public ResultInfo adminAccountUpdate(UserInfoVo objUserInfo);
	// 관리자 목록
	public List<UserInfoVo> getAdminOrUserList(String strAdminId, String strStartDate, String strEndDate, Integer intStateCode, int intStartRow, int intEndRow, String strRole);
	// 관리자 수
	public int getAdminOrUserTotalCount(String strAdminId, String strStartDate, String strEndDate, Integer intStateCode, String strRole);
	// 관리자 상태 별 수
	public List<UserInfoVo> getAdminOrUserCountByStatus(String strRole);

	
	// 관리자 상세정보
	public UserInfoVo getAdminOrUserDetailInfo(int intAdminNo, String strRole);
	// 관리자 상세내역
	public List<UserInfoVo> getAdminOrUserHistory(int intAdminNo);
	// 관리자 계정 비활성화
	public ResultInfo stopAdminAccount(String strAdminId, String strStopAdminId, String strReason);
	// 관리자 계정 활성화
	public ResultInfo activateAdminAccount(String strAdminId, String strActAdminId);
	// 관리자 계정 삭제
	public ResultInfo deleteAdminAccount(String strDelAdminId);
	
	
	// 관리자 계정 수정
	public ResultInfo updateAdminAccount(UserInfoVo objAdminInfo);
}
