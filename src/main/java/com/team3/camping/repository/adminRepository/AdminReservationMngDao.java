package com.team3.camping.repository.adminRepository;

import java.util.List;

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

public interface AdminReservationMngDao {

	// 캠핑장 예약 목록
	public List<ReservationVo> getUserCampReservationList(String strUserAccount, String strProvinceName, String strCityGunGuName, String strFacilityName, String strStartDate
			                                             ,String strEndDate, Integer intStateCode, int StartRow, int intEndRow);
	// 캠핑장 예약 갯수
	public int getUserCampReservationTotalCount(String strUserAccount, String strProvinceName, String strCityGunGuName, String strFacilityName, String strStartDate
                                               ,String strEndDate, Integer intStateCode);
	// 캠핑장 상태 별 갯수
	public List<ReservationVo> getCampReservationCountByStatus();
	// 캠핑장 예약 상세정보
	public ReservationVo getReservationDetailInfo(int intReservationNo);
	// 캠핑장 예약 상세내역
	public List<ReservationVo> getReservationHistoryList(int intReservationNo);
	
	
	// 캠핑장 예약정보 보류
	public ResultInfo holdCampReservation(ReservationVo objReservation);
	// 캠핑장 예약정보 활성화
	public ResultInfo activateReservation(ReservationVo objReservation);
	// 캠핑장 예약정보 제거
	public ResultInfo deleteReservation(ReservationVo objReservation);
}
