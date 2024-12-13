package com.team3.camping.repository.adminRepository;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

@Repository
public class AdminCampMngDaoImpl implements AdminCampMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminCampMngMapper.";
	
	// 캠핑장 비활성화
	@Override
	public ResultInfo campInfoStatusDeactivate(int intCampNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("ADMIN_CAMPSTATUS_STOP")
						                 .declareParameters(
						                   		            new SqlParameter("pi_intCampNo",    Types.INTEGER)
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
						                   		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCampNo", intCampNo);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminCampingMngDao][campInfoStatusDeactivate : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 캠핑장 활성화
	@Override
	public ResultInfo campInfoStatusActivate(int intCampNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("ADMIN_CAMPSTATUS_ACT")
						                 .declareParameters(
						                   		            new SqlParameter("pi_intCampNo",    Types.INTEGER)
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
						                   		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCampNo", intCampNo);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminCampingMngDao][campInfoStatusDeactivate : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 캠핑장 지역(대분류)
	@Override
	public List<CampInfoVo> groupByProvinceName() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "groupByProvinceName");
			
		} catch (Exception ex) {
			return null;
		}
	}

	// 캠핑장 지역(소분류)
	@Override
	public List<CampInfoVo> groupByCityGunGuName(String strProvinceName) {

		try {
			return sqlSession.selectList(MAPPER_PATH + "groupByCityGunGuName", strProvinceName);
			
		} catch (Exception ex) {
			return null;
		}
	}

	// 사업주체 추출
	@Override
	public List<CampInfoVo> groupByBusinessEntity() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "groupByBusinessEntity");
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	// 캠핑장 개수
	public int getCampTotalCount(String strBusinessEntity, String  strFacilityName, String  strProvinceName, String  strCityGunGuName, String  strStartDate
                                ,String  strEndDate, Integer intStateCode) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("facilityName",   strFacilityName);
			inputParameter.put("businessEntity", strBusinessEntity);
			inputParameter.put("provinceName",   strProvinceName);
			inputParameter.put("cityGunGuName",  strCityGunGuName);
			inputParameter.put("startDate",      strStartDate);
			
			inputParameter.put("endDate",        strEndDate);
			inputParameter.put("stateCode",      intStateCode);
			
			return sqlSession.selectOne(MAPPER_PATH + "getCampTotalCount", inputParameter);
			
		} catch (Exception ex) {
			
			return 0;
		}
	}

	// 캠핑장 목록
	@Override
	public List<CampInfoVo> getCampInfoList(String strBusinessEntity, String strFacilityName, String strProvinceName, String strCityGunGuName, String strStartDate
			                               ,String strEndDate, Integer intStateCode, int intStartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("facilityName",   strFacilityName);
			inputParameter.put("businessEntity", strBusinessEntity);
			inputParameter.put("provinceName",   strProvinceName);
			inputParameter.put("cityGunGuName",  strCityGunGuName);
			inputParameter.put("startDate",      strStartDate);
			
			inputParameter.put("endDate",        strEndDate);
			inputParameter.put("stateCode",      intStateCode);
			inputParameter.put("startRow",       intStartRow);
			inputParameter.put("endRow",         intEndRow);
			
			return sqlSession.selectList(MAPPER_PATH + "getCampInfoList", inputParameter);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 상세정보
	@Override
	public CampInfoVo getCampInfoDetail(int intCampNo) {

		try {
			return sqlSession.selectOne(MAPPER_PATH + "getCampInfoDetail", intCampNo);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 이미지
	@Override
	public CampInfoVo getCampDetailImage(int intCampNo) {

		try {
			CampInfoVo objCampImage = sqlSession.selectOne(MAPPER_PATH + "getCampDetailImage", intCampNo);
			
			if (objCampImage.getImagePath() == null) {
				
				return null;
			} else {
				
				return objCampImage;
			}
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 이미지 리스트
	@Override
	public List<CampInfoVo> getCampDetailImageList(int intCampNo) {
		
		try {
			List<CampInfoVo> objCampImageList = sqlSession.selectList(MAPPER_PATH + "getCampDetailImageList", intCampNo);
			
			if (objCampImageList.get(0).getImagePath() == null) {
				
				return null;
			} else {
				return objCampImageList;
			}
		} catch (Exception ex) {

			return null;
		}
	}
	
	// 캠핑장 상태별 개수
	@Override
	public List<CampInfoVo> getCampCountByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampCountByStatus");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 캠핑장 등록
	@Override
	public int registCampInfo(CampInfoVo objCampInfo) {
		
		try {
			return sqlSession.insert(MAPPER_PATH + "registCampInfo", objCampInfo);
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return 0;
	}

	// 캠핑장 별 예약내역
	@Override
	public List<ReservationVo> getReservationListByCamp(int intCampNo, int intStartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("campNo",   intCampNo);
			inputParameter.put("startRow", intStartRow);
			inputParameter.put("endRow",   intEndRow);
			
			return sqlSession.selectList(MAPPER_PATH + "getReservationListByCamp", inputParameter);
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 캠핑장 별 예약 갯수
	@Override
	public int getReservationTotalCountByCamp(int intCampNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "getReservationTotalCountByCamp", intCampNo);
			
		} catch (Exception ex) {

			return 0;
		}
	}
}
