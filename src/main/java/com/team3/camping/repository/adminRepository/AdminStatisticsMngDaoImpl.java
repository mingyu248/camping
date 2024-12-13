package com.team3.camping.repository.adminRepository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.UserInfoVo;

@Repository
public class AdminStatisticsMngDaoImpl implements AdminStatisticsMngDao {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminStatisticsMngMapper.";
	
	// 최근 30일간 회원 현황(자체 서비스 회원)
	@Override
	public List<UserInfoVo> getUserStatisticsByThirtyDays() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getUserStatisticsByThirtyDays");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 최근 30일간 회원 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByThirtyDays() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialStatisticsByThirtyDays");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 최근 30일 간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByThiryDays() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationStatisticsByThiryDays");
			
		} catch (Exception ex) {

			return null;
		}
	}
	
	// 남녀 성비(자체 서비스 회원)
	@Override
	public List<UserInfoVo> getUserStatisticsByGender() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getUserStatisticsByGender");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 회원 연령대 현황(자체 서비스)
	@Override
	public List<UserInfoVo> getUserStatisticsByAgeGroup() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getUserStatisticsByAgeGroup");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 회원 상태 현황(자체 서비스)
	@Override
	public List<UserInfoVo> getUserStatisticsByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getUserStatisticsByStatus");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 플랫폼 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByPlatform() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialStatisticsByPlatform");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 회원 성비(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByGender() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialStatisticsByGender");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 회원 연령대 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByAgeGroup() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialStatisticsByAgeGroup");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 회원 상태 현황(소셜 로그인 회원)
	@Override
	public List<UserInfoVo> getSocialStatisticsByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialStatisticsByStatus");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 지역 별 캠핑장 현황
	@Override
	public List<CampInfoVo> getCampStatisticsByProvince() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampStatisticsByProvince");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 시/군/구 별 캠핑장 현황
	@Override
	public List<CampInfoVo> getCampStatisticsByCityGunGu(String strProvinceName) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampStatisticsByCityGunGu", strProvinceName);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 문의 상태 별 현황
	@Override
	public List<HelpVo> getHelpStatisticsByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getHelpStatisticsByStatus");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 주간 문의 현황
	@Override
	public List<HelpVo> getHelpStatisticsByRegWeek() {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getHelpStatisticsByRegWeek");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 월간 문의 현황
	@Override
	public List<HelpVo> getHelpStatisticsByRegMonth() {
	
		try {
			return sqlSession.selectList(MAPPER_PATH + "getHelpStatisticsByRegMonth");
			
		} catch (Exception ex) {

			return null;
		}
	}
	
	// 지역별 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByProvince() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationStatisticsByProvince");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 시/군/구 별 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByCityGunGu(String strProvinceName) {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationStatisticsByCityGunGu", strProvinceName);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 주간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByRegWeek() {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationStatisticsByRegWeek");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 월간 예약 현황
	@Override
	public List<ReservationVo> getReservationStatisticsByMonth() {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationStatisticsByRegMonth");
			
		} catch (Exception ex) {

			return null;
		}
	}
}