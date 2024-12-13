package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

public interface AdminCampMngService {

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
	public PagingUtil getCampInfoList(String strBusinessEntity, String  strFacilityName, String  strProvinceName, String  strCityGunGuName, String  strStartDate
			                         ,String  strEndDate, Integer intStateCode, int intPage, int intLimit);
	// 캠핑장 상세정보
	public CampInfoVo getCampInfoDetail(int intCampNo);
	// 캠핑장 이미지
	public CampInfoVo getCampDetailImage(int intCampNo);
	// 캠핑장 이미지 리스트
	public List<CampInfoVo> getCampDetailImageList(int intCampNo);
	// 캠핑장 상태별 개수
	public List<CampInfoVo> getCampCountByStatus();
	
	
	// 캠핑장 등록
	public ResultInfo registCampInfo(MultipartFile file);
	// 캠핑장 별 예약 내역
	public PagingUtil getReservationListByCamp(int intCampNo, int intPage, int intLimit);
}
