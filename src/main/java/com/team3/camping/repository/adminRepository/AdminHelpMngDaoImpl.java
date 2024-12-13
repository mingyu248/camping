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

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.ResultInfo;

@Repository
public class AdminHelpMngDaoImpl implements AdminHelpMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminHelpMngMapper.";
	
	// 문의 답변
	@Override
	public ResultInfo adminInsertHelpAnswer(HelpVo objHelpVo) {
		
		ResultInfo objResultInfo = new ResultInfo();

		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_HELPANSWER_INS")
										 .declareParameters(
										  				    new SqlParameter("pi_intHelpNo",    Types.INTEGER)
														   ,new SqlParameter("pi_strAnswer",    Types.VARCHAR)
														   ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
																
														   ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
														    );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intHelpNo",  objHelpVo.getHelpNo());
			inputParameter.put("pi_strAnswer",  objHelpVo.getAnswer());
			inputParameter.put("pi_strAdminId", objHelpVo.getAdminId());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminHelpMngDao][adminInsertHelpAnswer : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 문의 목록
	@Override
	public List<HelpVo> getHelpList(String strHelpTitle, String strUserId, String strStartDate, String strEndDate, Integer intStateCode
			                       ,int intStartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("helpTitle", strHelpTitle);
			inputParameter.put("userId",    strUserId);
	    	inputParameter.put("startDate", strStartDate);
	    	inputParameter.put("endDate",   strEndDate);
	    	inputParameter.put("stateCode", intStateCode);
	    	
	    	inputParameter.put("startRow",  intStartRow);
	    	inputParameter.put("endRow",    intEndRow);
	    	
	    	return sqlSession.selectList(MAPPER_PATH + "getHelpList", inputParameter);
	    	
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 문의 글 수
	@Override
	public int getHelpTotalCount(String strHelpTitle, String strUserId, String strStartDate, String strEndDate, Integer intStateCode) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("helpTitle", strHelpTitle);
			inputParameter.put("userId",    strUserId);
	    	inputParameter.put("startDate", strStartDate);
	    	inputParameter.put("endDate",   strEndDate);
	    	inputParameter.put("stateCode", intStateCode);
	    	
	    	return sqlSession.selectOne(MAPPER_PATH + "getHelpTotalCount", inputParameter);
	    	
		} catch (Exception e) {

			return 0;
		}
	}

	// 문의 상세
	@Override
	public HelpVo getUserHelpDetail(int intHelpNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "getUserHelpDetail", intHelpNo);
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 문의 상태 별 개수
	@Override
	public List<HelpVo> getHelpCountByStatus() {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getHelpCountByStatus");
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 문의 삭제
	@Override
	public ResultInfo adminDeleteUserHelp(int intHelpNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
										 .withProcedureName("ADMIN_HELP_DEL")
										 .declareParameters(
										  				    new SqlParameter("pi_intHelpNo",    Types.INTEGER)
																
														   ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
														   ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
														    );
			
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intHelpNo",  intHelpNo);
			
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
