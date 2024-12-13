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

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.User;

@Repository
public class AdminSocialAccountMngDaoImpl implements AdminSocialAccountMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminSocialAccountMngMapper.";
	
	// 소셜 플랫폼 추출
	@Override
	public List<User> getSocialAccountPlatform() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialAccountPlatform");
			
		} catch (Exception ex) {
			return null;
		}
	}

	// 소셜 회원 목록
	@Override
	public List<UserInfoVo> getSocialAccountList(String strUserEmailOrId, String strPlatform, String strStartDate, String strEndDate, Integer intStateCode
			                                    ,int intStartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("emailOrId", strUserEmailOrId);
			inputParameter.put("platform",  strPlatform);
			inputParameter.put("startDate", strStartDate);
			inputParameter.put("endDate",   strEndDate);
			inputParameter.put("stateCode", intStateCode);
			
			inputParameter.put("startRow",  intStartRow);
			inputParameter.put("endRow",    intEndRow);
			
			return sqlSession.selectList(MAPPER_PATH + "getSocialAccountList", inputParameter);
			
		} catch (Exception ex) {
			return null;
		}
	}

	// 소셜 회원 검색별 수
	@Override
	public int getSocialAccountTotalCount(String strUserEmailOrId, String strPlatform, String strStartDate, String strEndDate, Integer intStateCode) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("emailOrId", strUserEmailOrId);
			inputParameter.put("platform",  strPlatform);
			inputParameter.put("startDate", strStartDate);
			inputParameter.put("endDate",   strEndDate);
			inputParameter.put("stateCode", intStateCode);
			
			return sqlSession.selectOne(MAPPER_PATH + "getSocialAccountTotalCount", inputParameter);
			
		} catch (Exception ex) {
			
			return 0;
		}
	}

	// 소셜 회원 상세정보
	@Override
	public UserInfoVo getSocialAccountDetail(int intUserNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "getSocialAccountDetail", intUserNo);
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 소셜 회원 상세내역
	@Override
	public List<UserInfoVo> getSocialAccountHistory(int intUserNo) {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialAccountHistory", intUserNo);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 소셜 회원 정지
	@Override
	public ResultInfo stopSocialAccount(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_SOCIAL_STOP")
					                     .declareParameters(
					                    		            new SqlParameter("pi_strStopEmail", Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strReason",    Types.VARCHAR)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strStopEmail", objUserInfo.getUserEmail());
			inputParameter.put("pi_strAdminId",   objUserInfo.getAdminId());
			inputParameter.put("pi_strReason",    objUserInfo.getReason());
			
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

	// 소셜 계정 활성화
	@Override
	public ResultInfo activateSocialAccount(UserInfoVo objUserInfo) {

		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_SOCIAL_ACT")
					                     .declareParameters(
					                    		            new SqlParameter("pi_strActEmail",  Types.VARCHAR)
					                    		           ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strActEmail", objUserInfo.getUserEmail());
			inputParameter.put("pi_strAdminId",  objUserInfo.getAdminId());
			
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

	// 소셜 계정 삭제
	@Override
	public ResultInfo deleteSocialAccount(UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_SOCIAL_DEL")
					                     .declareParameters(
					                    		            new SqlParameter("pi_strDelUserEmail",  Types.VARCHAR)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal",     Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal",     Types.VARCHAR)
					                    		            );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_strDelUserEmail", objUserInfo.getUserEmail());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 소셜 상태별 개수
	@Override
	public List<UserInfoVo> getSocialCountByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getSocialCountByStatus");
			
		} catch (Exception ex) {
			
			return null;
		}
	}
}
