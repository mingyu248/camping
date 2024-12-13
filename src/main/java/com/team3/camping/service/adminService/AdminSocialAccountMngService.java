package com.team3.camping.service.adminService;

import java.util.List;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.User;

public interface AdminSocialAccountMngService {

	// 소셜 플랫폼 추출
	public List<User> getSocialAccountPlatform();
	// 소셜 회원 목록
	public PagingUtil getSocialAccountList(String strUserEmailOrId, String strPlatform, String strStartDate, String strEndDate, Integer intStateCode
			                              ,int intPage, int intLimit);
	// 소셜 회원 상세정보
	public UserInfoVo getSocialAccountDetail(int intUserNo);
	// 소셜 회원 상세내역
	public List<UserInfoVo> getSocialAccountHistory(int intUserNo);
	// 소셜 회원 정지
	public ResultInfo stopSocialAccount(UserInfoVo objUserInfo);
	
	
	// 소셜 계정 활성화
	public ResultInfo activateSocialAccount(UserInfoVo objUserInfo);
	// 소셜 계정 삭제
	public ResultInfo deleteSocialAccount(UserInfoVo objUserInfo);
	// 소셜 상태별 개수
	public List<UserInfoVo> getSocialCountByStatus();
}
