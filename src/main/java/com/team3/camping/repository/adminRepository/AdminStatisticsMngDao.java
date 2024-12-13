package com.team3.camping.repository.adminRepository;

import java.util.List;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.UserInfoVo;

public interface AdminStatisticsMngDao {
	
	// 최근 30일간 회원 현황(자체 서비스 회원)
	public List<UserInfoVo> getUserStatisticsByThirtyDays();
	// 최근 30일간 회원 현황(소셜 로그인 회원)
	public List<UserInfoVo> getSocialStatisticsByThirtyDays();
	// 최근 30일간 예약 현황
	public List<ReservationVo> getReservationStatisticsByThiryDays();
	// 회원 성비(자체 서비스 회원)
	public List<UserInfoVo> getUserStatisticsByGender();
	// 회원 연령대 현황(자체 서비스 회원)
	public List<UserInfoVo> getUserStatisticsByAgeGroup();
	
	
	// 회원 상태 현황(자체 서비스 회원)
	public List<UserInfoVo> getUserStatisticsByStatus();
	// 플랫폼 현황(소셜 로그인 회원)
	public List<UserInfoVo> getSocialStatisticsByPlatform();
	// 회원 성비(소셜 로그인 회원)
	public List<UserInfoVo> getSocialStatisticsByGender();
	// 회원 연령대 현황(소셜 로그인 회원)
	public List<UserInfoVo> getSocialStatisticsByAgeGroup();
	// 회원 상태 현황(소셜 로그인 회원)
	public List<UserInfoVo> getSocialStatisticsByStatus();
	
	
	// 지역별 캠핑장 현황
	public List<CampInfoVo> getCampStatisticsByProvince();
	// 시/군/구 별 캠팡징 현황
	public List<CampInfoVo> getCampStatisticsByCityGunGu(String strProvinceName);
	// 문의 상태 별 현황
	public List<HelpVo> getHelpStatisticsByStatus();
	// 주간 문의 현황
	public List<HelpVo> getHelpStatisticsByRegWeek();
	// 월간 문의 현황
	public List<HelpVo> getHelpStatisticsByRegMonth();
	
	
	// 지역별 예약 현황
	public List<ReservationVo> getReservationStatisticsByProvince();
	// 시/군/구 별 예약 현황
	public List<ReservationVo> getReservationStatisticsByCityGunGu(String strProvinceName);
	// 주간 예약 현황
	public List<ReservationVo> getReservationStatisticsByRegWeek();
	// 월간 예약 현황
	public List<ReservationVo> getReservationStatisticsByMonth();
}
