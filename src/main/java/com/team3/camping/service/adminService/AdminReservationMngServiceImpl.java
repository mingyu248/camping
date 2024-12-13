package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.adminRepository.AdminReservationMngDao;

@Service
public class AdminReservationMngServiceImpl implements AdminReservationMngService {

	@Autowired
	private AdminReservationMngDao adminReservationMngDao;
	
	// 캠핑장 예약 목록
	@Override
	public PagingUtil getUserCampReservationList(String strUserAccount, String strProvinceName, String strCityGunGuName, String strFacilityName, String strStartDate
			                                    ,String strEndDate, Integer intStateCode, int intPage, int intLimit) {
		
		PagingUtil objPagingUtil = new PagingUtil();
		
		objPagingUtil.setCurrentPage(intPage);
		objPagingUtil.setLimit(intLimit);
		
		try {
			List<ReservationVo> objReservationList = adminReservationMngDao.getUserCampReservationList(strUserAccount, strProvinceName, strCityGunGuName, strFacilityName, strStartDate
					                                                                                  ,strEndDate, intStateCode, objPagingUtil.getStartRow(), objPagingUtil.getEndRow());
			int intReservationTotalCount = adminReservationMngDao.getUserCampReservationTotalCount(strUserAccount, strProvinceName, strCityGunGuName, strFacilityName, strStartDate
					                                                                              ,strEndDate, intStateCode);
			
			objPagingUtil.setTotalCount(intReservationTotalCount);
			objPagingUtil.calculateTotalPages(intReservationTotalCount);
			objPagingUtil.setObjReservationList(objReservationList);
			
			objPagingUtil.setPageRange(objPagingUtil.getPageRange());
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return objPagingUtil;
	}

	// 예약 상태 별 갯수
	@Override
	public List<ReservationVo> getCampReservationCountByStatus() {

		return adminReservationMngDao.getCampReservationCountByStatus();
	}

	// 캠핑장 예약 상세정보
	@Override
	public ReservationVo getReservationDetailInfo(int intReservationNo) {

		return adminReservationMngDao.getReservationDetailInfo(intReservationNo);
	}

	// 캠핑장 예약 상세내역
	@Override
	public List<ReservationVo> getReservationHistoryList(int intReservationNo) {

		return adminReservationMngDao.getReservationHistoryList(intReservationNo);
	}
	
	// 캠핑장 예약정보 보류
	@Override
	public ResultInfo holdCampReservation(ReservationVo objReservation) {

		return adminReservationMngDao.holdCampReservation(objReservation);
	}

	// 캠핑장 예약정보 활성화
	@Override
	public ResultInfo activateReservation(ReservationVo objReservation) {

		return adminReservationMngDao.activateReservation(objReservation);
	}

	// 캠핑장 예약정보 제거
	@Override
	public ResultInfo deleteReservation(ReservationVo objReservation) {

		return adminReservationMngDao.deleteReservation(objReservation);
	}
}