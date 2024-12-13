package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.adminRepository.AdminStatisticsMngDao;

@Service
public class AdminStatisticsMngServiceImpl implements AdminStatisticsMngService {

	@Autowired
	private AdminStatisticsMngDao adminStatisticsMngDao;
	
	// 최근 30일간 회원 현황(자체 서비스 회원)
	@Override
	public List<UserInfoVo> getUserStatisticsByThirtyDays() {
		
		return adminStatisticsMngDao.getUserStatisticsByThirtyDays();
	}

	// 최근 30일간 회원 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByThirtyDays() {

		return adminStatisticsMngDao.getSocialStatisticsByThirtyDays();
	}

	// 최근 30일간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByThiryDays() {

		return adminStatisticsMngDao.getReservationStatisticsByThiryDays();
	}
	
	// 남녀 성비(자체 서비스 회원)
	@Override
	public List<UserInfoVo> getUserStatisticsByGender() {
		
		return adminStatisticsMngDao.getUserStatisticsByGender();
	}

	// 회원 연령대 현황(자체 서비스)
	@Override
	public List<UserInfoVo> getUserStatisticsByAgeGroup() {

		return adminStatisticsMngDao.getUserStatisticsByAgeGroup();
	}

	// 회원 상태 현황(자체 서비스)
	@Override
	public List<UserInfoVo> getUserStatisticsByStatus() {

		return adminStatisticsMngDao.getUserStatisticsByStatus();
	}

	// 플랫폼 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByPlatform() {
		
		return adminStatisticsMngDao.getSocialStatisticsByPlatform();
	}

	// 회원 성비(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByGender() {
		
		return adminStatisticsMngDao.getSocialStatisticsByGender();
	}

	// 회원 연령대 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByAgeGroup() {
		
		return adminStatisticsMngDao.getSocialStatisticsByAgeGroup();
	}

	// 회원 상태 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByStatus() {
		
		return adminStatisticsMngDao.getUserStatisticsByStatus();
	}

	// 지역 별 캠핑장 현황
	@Override
	public List<CampInfoVo> getCampStatisticsByProvince() {

		return adminStatisticsMngDao.getCampStatisticsByProvince();
	}

	// 시/군/구 별 캠핑장 현황
	@Override
	public List<CampInfoVo> getCampStatisticsByCityGunGu(String strProvinceName) {

		return adminStatisticsMngDao.getCampStatisticsByCityGunGu(strProvinceName);
	}

	// 문의 상태 별 현황
	@Override
	public List<HelpVo> getHelpStatisticsByStatus() {

		return adminStatisticsMngDao.getHelpStatisticsByStatus();
	}

	// 주간 문의 현황
	@Override
	public List<HelpVo> getHelpStatisticsByRegWeek() {

		return adminStatisticsMngDao.getHelpStatisticsByRegWeek();
	}

	// 월간 문의 현황
	@Override
	public List<HelpVo> getHelpStatisticsByRegMonth() {

		return adminStatisticsMngDao.getHelpStatisticsByRegMonth();
	}

	// 지역별 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByProvince() {
		
		return adminStatisticsMngDao.getReservationStatisticsByProvince();
	}

	// 시/군/구 별 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByCityGunGu(String strProvinceName) {
		
		return adminStatisticsMngDao.getReservationStatisticsByCityGunGu(strProvinceName);
	}

	// 주간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByRegWeek() {
		
		return adminStatisticsMngDao.getReservationStatisticsByRegWeek();
	}

	// 월간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByMonth() {
		
		return adminStatisticsMngDao.getReservationStatisticsByMonth();
	}
}
