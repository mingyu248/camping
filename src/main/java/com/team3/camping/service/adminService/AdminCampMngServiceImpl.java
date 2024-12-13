package com.team3.camping.service.adminService;

import java.io.InputStreamReader;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.adminRepository.AdminCampMngDao;

import oracle.sql.CHAR;

@Service
public class AdminCampMngServiceImpl implements AdminCampMngService {

	@Autowired
	private AdminCampMngDao adminCampMngDao;
	
	// 캠핑장 비활성화
	@Override
	public ResultInfo campInfoStatusDeactivate(int intCampNo) {
		
		return adminCampMngDao.campInfoStatusDeactivate(intCampNo);
	}

	// 캠핑장 활성화
	@Override
	public ResultInfo campInfoStatusActivate(int intCampNo) {
		
		return adminCampMngDao.campInfoStatusActivate(intCampNo);
	}

	// 캠핑자 지역(대분류)
	@Override
	public List<CampInfoVo> groupByProvinceName() {

		return adminCampMngDao.groupByProvinceName();
	}

	// 캠핑장 지역(소분류)
	@Override
	public List<CampInfoVo> groupByCityGunGuName(String strProvinceName) {

		return adminCampMngDao.groupByCityGunGuName(strProvinceName);
	}

	// 사업주체 추출
	@Override
	public List<CampInfoVo> groupByBusinessEntity() {

		return adminCampMngDao.groupByBusinessEntity();
	}

	// 캠핑장 목록
	@Override
	public PagingUtil getCampInfoList(String strBusinessEntity, String strFacilityName, String strProvinceName, String strCityGunGuName, String strStartDate
			                         ,String strEndDate, Integer intStateCode, int intPage, int intLimit) {
		
		PagingUtil objPaginUtil = new PagingUtil();
		
		objPaginUtil.setCurrentPage(intPage);
		objPaginUtil.setLimit(intLimit);
		
		try {
			List<CampInfoVo> objCampList = adminCampMngDao.getCampInfoList(strBusinessEntity, strFacilityName, strProvinceName, strCityGunGuName, strStartDate
					                                                      ,strEndDate, intStateCode, objPaginUtil.getStartRow(), objPaginUtil.getEndRow());
			int intCampTotalCount = adminCampMngDao.getCampTotalCount(strBusinessEntity, strFacilityName, strProvinceName, strCityGunGuName, strStartDate
					                                                 ,strEndDate, intStateCode);
			
			objPaginUtil.setTotalCount(intCampTotalCount);
			objPaginUtil.calculateTotalPages(intCampTotalCount);
			objPaginUtil.setObjCampList(objCampList);
			
			objPaginUtil.setPageRange(objPaginUtil.getPageRange());
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return objPaginUtil;
	}

	// 캠핑장 상세정보
	@Override
	public CampInfoVo getCampInfoDetail(int intCampNo) {

		return adminCampMngDao.getCampInfoDetail(intCampNo);
	}

	// 캠핑장 이미지
	@Override
	public CampInfoVo getCampDetailImage(int intCampNo) {

		return adminCampMngDao.getCampDetailImage(intCampNo);
	}

	// 캠핑장 이미지 리스트
	@Override
	public List<CampInfoVo> getCampDetailImageList(int intCampNo) {
		
		return adminCampMngDao.getCampDetailImageList(intCampNo);
	}
	
	// 캠핑장 상태별 개수
	@Override
	public List<CampInfoVo> getCampCountByStatus() {

		return adminCampMngDao.getCampCountByStatus();
	}

	// 캠핑장 등록
	@Override
	public ResultInfo registCampInfo(MultipartFile file) {
		
		int intRetVal = 0;
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
			
			String[] strNextLine;
			
			csvReader.readNext(); // 헤더 스킵
			
			while ((strNextLine = csvReader.readNext()) != null) {
				if (strNextLine.length < 43) { // 필요한 열의 수
			        objResultInfo.setIntFailedCount(objResultInfo.getIntFailedCount() + 1);
			        
			        continue; // 다음 행으로 넘어감
			    }
				CampInfoVo objCampInfo = new CampInfoVo();
				
				objCampInfo.setFacilityName(strNextLine[0]);
				objCampInfo.setCampingType(strNextLine[1]);
				objCampInfo.setProvinceName(strNextLine[2]);
				objCampInfo.setCityGunGuName(strNextLine[3]);
				objCampInfo.setLegalTownVillageDongName(strNextLine[4]);
				
				objCampInfo.setRiName(strNextLine[5]);
				objCampInfo.setLotNumber(strNextLine[6]);
				objCampInfo.setRoadName(strNextLine[7]);
				objCampInfo.setBuildingNumber(strNextLine[8]);
				objCampInfo.setLatitude(Double.parseDouble(strNextLine[9]));
				
				objCampInfo.setLongitude(Double.parseDouble(strNextLine[10]));
				objCampInfo.setPostalCode(strNextLine[11]);
				objCampInfo.setRoadAddress(strNextLine[12]);
				objCampInfo.setLandAddress(strNextLine[13]);
				objCampInfo.setTelNumber(strNextLine[14]);
				
				objCampInfo.setWebsite(strNextLine[15]);
				objCampInfo.setBusinessEntity(strNextLine[16]);
				objCampInfo.setWeekdayOperation(strNextLine[17].charAt(0));
				objCampInfo.setWeekendOperation(strNextLine[18].charAt(0));
				objCampInfo.setSpringOperation(strNextLine[19].charAt(0));
				
				objCampInfo.setSummerOperation(strNextLine[20].charAt(0));
				objCampInfo.setAutumnOperation(strNextLine[21].charAt(0));
				objCampInfo.setWinterOperation(strNextLine[22].charAt(0));
				objCampInfo.setElectricitySubfacility(strNextLine[23].charAt(0));
				objCampInfo.setHotWaterSubfacility(strNextLine[24].charAt(0));
				
				objCampInfo.setWirelessInternetSubfacility(strNextLine[25].charAt(0));
				objCampInfo.setFirewoodSalesSubfacility(strNextLine[26].charAt(0));
				objCampInfo.setWalkingTrailSubfacility(strNextLine[27].charAt(0));
				objCampInfo.setWaterPlayAreaSubfacility(strNextLine[28].charAt(0));
				objCampInfo.setPlaygroundSubfacility(strNextLine[29].charAt(0));
				
				objCampInfo.setMartSubfacility(strNextLine[30].charAt(0));
//				objCampInfo.setNumberOfToilets(Integer.parseInt(strNextLine[31]));
//				objCampInfo.setNumberOfShowers(Integer.parseInt(strNextLine[32]));
//				objCampInfo.setNumberOfSinks(Integer.parseInt(strNextLine[33]));
//				objCampInfo.setNumberOfFireExtinguishers(Integer.parseInt(strNextLine[34]));
//				
//				objCampInfo.setBedGlamping(strNextLine[35].charAt(0));
//				objCampInfo.setTVGlamping(strNextLine[36].charAt(0));
//				objCampInfo.setRefrigeratorGlamping(strNextLine[37].charAt(0));
//				objCampInfo.setWiredInternetGlamping(strNextLine[38].charAt(0));
//				objCampInfo.setInternalToiletGlamping(strNextLine[39].charAt(0));
//				
//				objCampInfo.setAirConditionerGlamping(strNextLine[40].charAt(0));
//				objCampInfo.setHeatingDeviceGlamping(strNextLine[41].charAt(0));
//				objCampInfo.setCookingUtensilsGlamping(strNextLine[42].charAt(0));
				objCampInfo.setFacilityFeatures(strNextLine[31]);
				objCampInfo.setFacilityIntroduction(strNextLine[32]);
				
				String dateString = strNextLine[33];
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
				try {
				    java.util.Date utilDate = formatter.parse(dateString);
				    Date           sqlDate  = new Date(utilDate.getTime());
				    
				    objCampInfo.setLastUpdatedDate(sqlDate);
				    
				} catch (ParseException parseEx) {
				    parseEx.printStackTrace();
				}
				intRetVal = adminCampMngDao.registCampInfo(objCampInfo);
				
				if (intRetVal > 0) {
					objResultInfo.setIntSuccessCount(objResultInfo.getIntSuccessCount() + 1);
				} else {
					objResultInfo.setIntFailedCount(objResultInfo.getIntFailedCount() + 1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("Exception : " + ex.toString());
		} 
		return objResultInfo;
	}

	// 캠핑장 별 예약 내역
	@Override
	public PagingUtil getReservationListByCamp(int intCampNo, int intPage, int intLimit) {

		PagingUtil objPagingUtil = new PagingUtil();
		
		objPagingUtil.setCurrentPage(intPage);
		objPagingUtil.setLimit(intLimit);
		
		try {
			List<ReservationVo> objReservationListByCamp = adminCampMngDao.getReservationListByCamp(intCampNo, objPagingUtil.getStartRow(), objPagingUtil.getEndRow());
			int intReservationTotalCountByCamp           = adminCampMngDao.getReservationTotalCountByCamp(intCampNo);
			
			objPagingUtil.setTotalCount(intReservationTotalCountByCamp);
			objPagingUtil.calculateTotalPages(intReservationTotalCountByCamp);
			objPagingUtil.setObjReservationList(objReservationListByCamp);
			
			objPagingUtil.setPageRange(objPagingUtil.getPageRange());
		} catch (Exception ex) {
			
		}
		return objPagingUtil;
	}
}
