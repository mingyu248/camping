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

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

@Repository
public class AdminReservationMngDaoImpl implements AdminReservationMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminReservationMngMapper.";
	
	// 회원 캠핑장 예약 목록
	@Override
	public List<ReservationVo> getUserCampReservationList(String strUserAccount, String strProvinceName, String strCityGunGuName, String strFacilityName, String strStartDate
			                                             ,String strEndDate, Integer intStateCode, int StartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("userAccount",   strUserAccount);
			inputParameter.put("provinceName",  strProvinceName);
			inputParameter.put("cityGunGuName", strCityGunGuName);
			inputParameter.put("facilityName",  strFacilityName);
			inputParameter.put("startDate",     strStartDate);
			
			inputParameter.put("endDate",       strEndDate);
			inputParameter.put("stateCode",     intStateCode);
			inputParameter.put("startRow",      StartRow);
			inputParameter.put("endRow",        intEndRow);
			
			return sqlSession.selectList(MAPPER_PATH + "getUserCampReservationList", inputParameter);
			
		} catch (Exception ex) {

			return null;
		}
	}
	
	// 캠핑장 예약 갯수
	@Override
	public int getUserCampReservationTotalCount(String strUserAccount, String strProvinceName, String strCityGunGuName, String strFacilityName, String strStartDate
			                                   ,String strEndDate, Integer intStateCode) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("userAccount",   strUserAccount);
			inputParameter.put("provinceName",  strProvinceName);
			inputParameter.put("cityGunGuName", strCityGunGuName);
			inputParameter.put("facilityName",  strFacilityName);
			inputParameter.put("startDate",     strStartDate);
			
			inputParameter.put("endDate",       strEndDate);
			inputParameter.put("stateCode",     intStateCode);
			
			return sqlSession.selectOne(MAPPER_PATH + "getUserCampReservationTotalCount", inputParameter);
		} catch (Exception ex) {
			return 0;
		}
	}

	// 캠핑장 상태 별 갯수
	@Override
	public List<ReservationVo> getCampReservationCountByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampReservationCountByStatus");
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 예약 상세정보
	@Override
	public ReservationVo getReservationDetailInfo(int intReservationNo) {

		try {
			return sqlSession.selectOne(MAPPER_PATH + "getReservationDetailInfo", intReservationNo);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 예약 상세내역
	@Override
	public List<ReservationVo> getReservationHistoryList(int intReservationNo) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getReservationHistoryList", intReservationNo);
			
		} catch (Exception ex) {

			return null;
		}
	}
	
	// 캠핑장 예약정보 보류
	@Override
	public ResultInfo holdCampReservation(ReservationVo objReservation) {
		
		ResultInfo objResultInfo = new ResultInfo();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_RESERVATION_HOLD")
										 .declareParameters(
										  				    new SqlParameter("pi_strReservationNo", Types.INTEGER)
														   ,new SqlParameter("pi_strAdminId",       Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal",     Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal",     Types.VARCHAR)
														    ); 
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strReservationNo",  objReservation.getReservationNo());
			inputParameter.put("pi_strAdminId",        objReservation.getAdminId());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 캠핑장 예약정보 활성화
	@Override
	public ResultInfo activateReservation(ReservationVo objReservation) {
		
		ResultInfo objResultInfo = new ResultInfo();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_RESERVATION_ACT")
										 .declareParameters(
										  				    new SqlParameter("pi_strReservationNo", Types.INTEGER)
														   ,new SqlParameter("pi_strAdminId",       Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal",     Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal",     Types.VARCHAR)
														    ); 
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strReservationNo",  objReservation.getReservationNo());
			inputParameter.put("pi_strAdminId",        objReservation.getAdminId());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 캠핑장 예약정보 제거
	@Override
	public ResultInfo deleteReservation(ReservationVo objReservation) {
		
		ResultInfo objResultInfo = new ResultInfo();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_RESERVATION_DEL")
										 .declareParameters(
										  				    new SqlParameter("pi_strReservationNo", Types.INTEGER)
																
														   ,new SqlOutParameter("po_intRetVal",     Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal",     Types.VARCHAR)
														    ); 
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strReservationNo",  objReservation.getReservationNo());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
}