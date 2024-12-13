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
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;

@Repository
public class AdminReviewMngDaoImpl implements AdminReviewMngDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.adminMapper.AdminReviewMngMapper.";
	
	// 후기 정지
	@Override
	public ResultInfo userCampReviewDeactivate(ReviewVo objReviewVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("ADMIN_REVIEW_STOP")
						                 .declareParameters(
						                   		            new SqlParameter("pi_intReviewNo",  Types.INTEGER)
						                   		           ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
						                   		           ,new SqlParameter("pi_strReason",    Types.VARCHAR)
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
						                   		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intReviewNo", objReviewVo.getReviewNo());
			inputParameter.put("pi_strAdminId",  objReviewVo.getAdminId());
			inputParameter.put("pi_strReason",   objReviewVo.getReason());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userReviewDeactivate] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 후기 활성화
	@Override
	public ResultInfo userReviewActivate(ReviewVo objReviewVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("ADMIN_REVIEW_ACT")
						                 .declareParameters(
						                   		            new SqlParameter("pi_intReviewNo",  Types.INTEGER)
						                   		           ,new SqlParameter("pi_strAdminId",   Types.VARCHAR)
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
						                   		            );
					
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intReviewNo", objReviewVo.getReviewNo());
			inputParameter.put("pi_strAdminId",  objReviewVo.getAdminId());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userReviewActivate] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 후기 삭제
	@Override
	public ResultInfo userReviewDelete(ReviewVo objReviewVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_REVIEW_DEL")
					                     .declareParameters(
					                    		            new SqlParameter("pi_intReviewNo",  Types.INTEGER)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intReviewNo", objReviewVo.getReviewNo());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int intRetVal    = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
					                     
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userReviewDelete] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 캠핑장 후기 목록
	@Override
	public List<ReviewVo> getCampReivewList(int intCampNo, String strReivewTitle, String strUserAccount, String strStartDate, String strEndDate
			                               ,Integer intStateCode ,int intStartRow, int intEndRow) {
		
		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("campNo",      intCampNo);
			inputParameter.put("reviewTitle", strReivewTitle);
			inputParameter.put("userAccount", strUserAccount);
			inputParameter.put("startDate",   strStartDate);
			inputParameter.put("endDate",     strEndDate);
			
			inputParameter.put("stateCode",   intStateCode);
			inputParameter.put("startRow",    intStartRow);
			inputParameter.put("endRow",      intEndRow);
			
			return sqlSession.selectList(MAPPER_PATH + "getCampReivewList", inputParameter);
			
		} catch (Exception ex) {
			
			return null;
		}
	}

	// 캠핑장 후기 개수
	@Override
	public int getCampReivewTotalCount(int intCampNo, String strReivewTitle, String strUserAccount, String strStartDate, String strEndDate
			                          ,Integer intStateCode) {

		Map<String, Object> inputParameter = new HashMap<>();
		
		try {
			inputParameter.put("campNo",      intCampNo);
			inputParameter.put("reviewTitle", strReivewTitle);
			inputParameter.put("userAccount", strUserAccount);
			inputParameter.put("startDate",   strStartDate);
			inputParameter.put("endDate",     strEndDate);
			
			inputParameter.put("stateCode",   intStateCode);
			
			return sqlSession.selectOne(MAPPER_PATH + "getCampReivewTotalCount", inputParameter);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			return 0;
		}
	}

	// 캠핑장 후기 상세정보
	@Override
	public ReviewVo getCampReviewDetail(int intReviewNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "getCampReviewDetail", intReviewNo);
			
		} catch (Exception ex) {
			
			return null;
		}
	}
	
	// 캠핑장 후기 상세내역
	@Override
	public List<ReviewVo> getCampReviewHistory(int intReviewNo) {

		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampReviewHistory", intReviewNo);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 후기 댓글
	@Override
	public List<ReviewCommentVo> getCampReviewCommentList(int intReviewNo) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "getCampReviewCommentList", intReviewNo);
			
		} catch (Exception ex) {

			return null;
		}
	}

	// 캠핑장 후기 댓글 갯수
	@Override
	public int getCampReviewCommentCount(int intReviewNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "getCampReviewCommentCount", intReviewNo);
			
		} catch (Exception ex) {

			return 0;
		}
	}

	// 댓글 정지
	@Override
	public ResultInfo userCommentDeactivate(int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_COMMENT_STOP")
					                     .declareParameters(
					                    		            new SqlParameter("pi_intCommentNo",  Types.INTEGER)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCommentNo", intCommentNo);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int intRetVal    = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
					                     
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userCommentDeactivate] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 댓글 활성화
	@Override
	public ResultInfo userCommentActivate(int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_COMMENT_ACT")
					                     .declareParameters(
					                    		            new SqlParameter("pi_intCommentNo",  Types.INTEGER)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCommentNo", intCommentNo);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int intRetVal    = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
					                     
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userCommentActivate] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}

	// 댓글 삭제
	@Override
	public ResultInfo userCommentDelete(int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
					                     .withProcedureName("ADMIN_COMMENT_DEL")
					                     .declareParameters(
					                    		            new SqlParameter("pi_intCommentNo",  Types.INTEGER)
					                    		           
					                    		           ,new SqlOutParameter("po_intRetVal", Types.INTEGER)
					                    		           ,new SqlOutParameter("po_strRetVal", Types.VARCHAR)
					                    		            );
			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCommentNo", intCommentNo);
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int intRetVal    = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);
					                     
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userCommentDelete] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
}