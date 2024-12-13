package com.team3.camping.repository.adminRepository;

import java.sql.Types;
import java.util.ArrayList;
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

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AdminAccountMngDaoImpl implements AdminAccountMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminManageMapper.";
	
	// 관리자 아이디 중복확인
	@Override
	public ResultInfo adminIdDuplicateCheck(String strAdminId) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_ACCOUNT_DUP")
					                     .declareParameters(
					                    		            new SqlParameter("pi_strAdminId",    Types.VARCHAR)
					                    		            
					                    		            ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
						                    		        ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strAdminId", strAdminId);
			
			Map<String, Object> outputMap = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputMap.get("po_intRetVal");
			String strRetVal = (String) outputMap.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminIdDuplicateCheck]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
	
	// 관리자 계정생성
	@Override
	public ResultInfo createAdminAccount(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_ACCOUNT_INS")
					                     .declareParameters(
					                    		            new SqlParameter("pi_strAdminId",     Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strJoinAdminId", Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strAdminPwd",    Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strAdminName",   Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strAdminEmail",  Types.VARCHAR)
					                    		           
					                    		           ,new SqlParameter("pi_strAdminTelNum", Types.VARCHAR)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
					                    		            );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strAdminId",     objUserInfo.getAdminId());
			inputParameter.put("pi_strJoinAdminId", objUserInfo.getUserId());
			inputParameter.put("pi_strAdminPwd",    objUserInfo.getUserPwd());
			inputParameter.put("pi_strAdminName",   objUserInfo.getUserName());
			inputParameter.put("pi_strAdminEmail",  objUserInfo.getUserEmail());

			inputParameter.put("pi_strAdminTelNum", objUserInfo.getUserTelNum());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][createAdminAccount]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 계정 비활성화
	@Override
	public ResultInfo adminAccountDeactivate(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_ACCOUNT_STOP")
					                     .declareParameters(
					                   		                new SqlParameter("pi_strStopId",    Types.VARCHAR)
					                   		               ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
					                   		               ,new SqlParameter("pi_strReason",    Types.VARCHAR)

					                   		               ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                   		               ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                   		                );
					
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strStopId",  objUserInfo.getUserId());
			inputParameter.put("pi_strAdminId", objUserInfo.getAdminId());
			inputParameter.put("pi_strReason",  objUserInfo.getReason());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountDeactivate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 계정 활성화
	@Override
	public ResultInfo adminAccountActivate(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("ADMIN_ACCOUNT_ACT")
						                 .declareParameters(
						                  		            new SqlParameter("pi_strActId",     Types.VARCHAR)
						                  		           ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
						
    					                  		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                  		               ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
						                  		            );

		Map<String, Object> inputParameter = new HashMap<>();
		
		inputParameter.put("pi_strActId",   objUserInfo.getUserId());
		inputParameter.put("pi_strAdminId", objUserInfo.getAdminId());
		
		Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
		
		int    intRetVal = (int)    outputParameter.get("po_intRetVal");
		String strRetVal = (String) outputParameter.get("po_strRetVal");
		
		objResultInfo.setIntRetVal(intRetVal);
		objResultInfo.setStrRetVal(strRetVal);
		
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountActivate]============ Exception : " + ex.getMessage());
		}
		
		return objResultInfo;
	}

	// 관리자 계정 삭제(사용자)
	@Override
	public ResultInfo adminAccountDelete(String strAdminId) {

		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
							             .withProcedureName("ADMIN_ACCOUNT_DEL")
							             .declareParameters(
							              		            new SqlParameter("pi_strSecessionId", Types.VARCHAR)
							
							              		           ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
							              		           ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
							              		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strSecessionId", strAdminId);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountDelete]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 회원 계정삭제
	@Override
	public ResultInfo userAccountDelete(String strUserId) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
		             					 .withProcedureName("ADMIN_USERACCOUNT_DEL")
		             					 .declareParameters(
		             							 			new SqlParameter("pi_strUserId", Types.VARCHAR)
		
		             							 		   ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
		             							 		   ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
		             							 			);

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strUserId", strUserId);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountDelete]============ Exception : " + ex.getMessage());
		}
		
		return objResultInfo;
	}

	// 관리자 계정 수정
	@Override
	public ResultInfo adminAccountUpdate(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_ACCOUNT_UPD")
										 .declareParameters(
												 			new SqlParameter("pi_strAdminId",     Types.VARCHAR)
												 		   ,new SqlParameter("pi_strUpdAdminId",  Types.VARCHAR)
												 		   ,new SqlParameter("pi_strAdminPwd",    Types.VARCHAR)
												 		   ,new SqlParameter("pi_strAdminName",   Types.VARCHAR)
												 		   ,new SqlParameter("pi_strAdminEmail",  Types.VARCHAR)

												 		   ,new SqlParameter("pi_strAdminTelNum",  Types.VARCHAR)
					
												 		   ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
												 		   ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
												 			);
					
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strAdminId",     objUserInfo.getAdminId());
			inputParameter.put("pi_strUpdAdminId",  objUserInfo.getUserId());
			inputParameter.put("pi_strAdminPwd",    objUserInfo.getUserPwd());
			inputParameter.put("pi_strAdminName",   objUserInfo.getUserName());
			inputParameter.put("pi_strAdminEmail",  objUserInfo.getUserEmail());

			inputParameter.put("pi_strAdminTelNum", objUserInfo.getUserTelNum());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountUpdate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 목록
	@Override
	public List<UserInfoVo> getAdminOrUserList(String strAdminId, String strStartDate, String strEndDate, Integer intStateCode, int intStartRow, int intEndRow, String strRole) {
		
	    List<UserInfoVo> objAdminList = new ArrayList<>();
	    Map<String, Object> inputParameter = new HashMap<>();
	    
	    try {
	    	inputParameter.put("adminId",   strAdminId);
	    	inputParameter.put("startDate", strStartDate);
	    	inputParameter.put("endDate",   strEndDate);
	    	inputParameter.put("stateCode", intStateCode);
	    	inputParameter.put("startRow",  intStartRow);
	    	
	    	inputParameter.put("endRow",    intEndRow);
	    	inputParameter.put("role",      strRole);
	    	
	    	objAdminList = sqlSession.selectList(MAPPER_PATH + "getAdminOrUserList", inputParameter);
		} catch (Exception ex) {
			// TODO: handle exception
		}
	    
	    return objAdminList;
	}

	@Override
	public int getAdminOrUserTotalCount(String strAdminId, String strStartDate, String strEndDate, Integer intStateCode, String strRole) {
		
		int intAdminCount = 0;
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("adminId",   strAdminId);
			inputParameter.put("startDate", strStartDate);
			inputParameter.put("endDate",   strEndDate);
			inputParameter.put("stateCode", intStateCode);
			inputParameter.put("role",      strRole);
			
			intAdminCount = sqlSession.selectOne(MAPPER_PATH + "getAdminOrUserTotalCount", inputParameter);
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return intAdminCount;
	}

	// 관리자 상태 별 수
	@Override
	public List<UserInfoVo> getAdminOrUserCountByStatus(String strRole) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getAdminOrUserCountByStatus", strRole);
			
		} catch (Exception ex) {
			return null;
		}
	}

	// 관리자 상세정보
	@Override
	public UserInfoVo getAdminOrUserDetailInfo(int intAdminNo, String strRole) {
		
		UserInfoVo objAdminDetail = new UserInfoVo();
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("adminNo", intAdminNo);
			inputParameter.put("role",    strRole);
			
			objAdminDetail = sqlSession.selectOne(MAPPER_PATH + "getAdminOrUserDetailInfo", inputParameter);
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return objAdminDetail;
	}

	// 관리자 상세내역
	@Override
	public List<UserInfoVo> getAdminOrUserHistory(int intAdminNo) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getAdminOrUserHistory", intAdminNo);
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	// 관리자 계정 비활성화
	@Override
	public ResultInfo stopAdminAccount(String strAdminId, String strStopAdminId, String strReason) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_ACCOUNT_STOP")
										 .declareParameters(
															new SqlParameter("pi_strStopId",    Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
														   ,new SqlParameter("pi_strReason",    Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
														   );
									
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strStopId",  strStopAdminId);
			inputParameter.put("pi_strAdminId", strAdminId);
			inputParameter.put("pi_strReason",  strReason);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountUpdate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 계정 활성화
	@Override
	public ResultInfo activateAdminAccount(String strAdminId, String strActAdminId) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_ACCOUNT_ACT")
										 .declareParameters(
															new SqlParameter("pi_strActId",    Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
														   );
									
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strActId",   strActAdminId);
			inputParameter.put("pi_strAdminId", strAdminId);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountUpdate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 계정삭제
	@Override
	public ResultInfo deleteAdminAccount(String strDelAdminId) {

		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_ACCOUNT_DEL")
										 .declareParameters(
														    new SqlParameter("pi_strAdminId",   Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
														   );
									
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strAdminId", strDelAdminId);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountUpdate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 관리자 정보 수정
	@Override
	public ResultInfo updateAdminAccount(UserInfoVo objAdminInfo) {

		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Id : " + objAdminInfo.getUserId());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Pwd : " + objAdminInfo.getUserPwd());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Name : " + objAdminInfo.getUserName());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Email : " + objAdminInfo.getUserEmail());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Tel Number : " + objAdminInfo.getUserTelNum());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== StateCode : " + objAdminInfo.getStateCode());
		log.info("========================================[AdminAccountMngDao][updateAdminAccount]======================================== Admin Id : " + objAdminInfo.getAdminId());
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_ACCOUNT_UPD")
										 .declareParameters(
														    new SqlParameter("pi_strAdminId",     Types.VARCHAR)
														   ,new SqlParameter("pi_strUpdAdminId",  Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminPwd",    Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminName",   Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminEmail",  Types.VARCHAR)

														   ,new SqlParameter("pi_strAdminTelNum", Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
														   );
									
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strAdminId",    objAdminInfo.getAdminId());
			inputParameter.put("pi_strUpdAdminId", objAdminInfo.getUserId());
			inputParameter.put("pi_strAdminPwd",   objAdminInfo.getUserPwd());
			inputParameter.put("pi_strAdminName",  objAdminInfo.getUserName());
			inputParameter.put("pi_strAdminEmail", objAdminInfo.getUserEmail());

			inputParameter.put("pi_strAdminTelNum", objAdminInfo.getUserTelNum());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminAccountMngDao][adminAccountUpdate]============ Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
}
