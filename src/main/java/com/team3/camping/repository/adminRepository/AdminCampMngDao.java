package com.team3.camping.repository.adminRepository;

import java.util.List;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

public interface AdminCampMngDao {

	// 캠핑장 비활성화
	public ResultInfo campInfoStatusDeactivate(int intCampNo);
	// 캠핑장 활성화
	public ResultInfo campInfoStatusActivate(int intCampNo);
	// 캠핑장 지역(대분류)
	public List<CampInfoVo> groupByProvinceName();
	// 캠핑장 지역(소분류)
	public List<CampInfoVo> groupByCityGunGuName(String strProvinceName);
	// 사업주체 추출
	public List<CampInfoVo> groupByBusinessEntity();
	
	
	// 캠핑장 목록
	public List<CampInfoVo> getCampInfoList(String strBusinessEntity, String  strFacilityName, String  strProvinceName, String  strCityGunGuName, String  strStartDate
                                           ,String  strEndDate, Integer intStateCode, int intStartRow, int intEndRow);
	// 캠핑장 개수
	public int getCampTotalCount(String strBusinessEntity, String  strFacilityName, String  strProvinceName, String  strCityGunGuName, String  strStartDate
                                ,String  strEndDate, Integer intStateCode);
	// 캠핑장 상세정보
	public CampInfoVo getCampInfoDetail(int intCampNo);
	// 캠핑장 이미지
	public CampInfoVo getCampDetailImage(int intCampNo);
	// 캠핑장 이미지 리스트
	public List<CampInfoVo> getCampDetailImageList(int intCampNo);
	// 캠핑장 상태별 개수
	public List<CampInfoVo> getCampCountByStatus();
	
	
	// 캠핑장 등록
	public int registCampInfo(CampInfoVo objCampInfo);
	// 캠핑장 별 예약 내역
	public List<ReservationVo> getReservationListByCamp(int intCampNo, int intStartRow, int intEndRow);
	// 캠핑장 별 예약 갯수
	public int getReservationTotalCountByCamp(int intCampNo);
}
