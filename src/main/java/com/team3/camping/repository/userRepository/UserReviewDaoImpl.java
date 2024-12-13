package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserReviewDaoImpl implements UserReviewDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.userMapper.ReviewMapper.";
	
	@Override
	public int getSequence() {
		
		return sqlSession.selectOne(MAPPER_PATH + "getSequence");
	}
	
	// 후기작성
	@Override
	public int registReview(ReviewVo objReview) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.insert(MAPPER_PATH + "reviewRegist", objReview);
				
		    objResultInfo.setIntRetVal(0);
		    objResultInfo.setStrRetVal("SUCCESS");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("후기 작성 중 에러");
		}
		return intRetVal;
	}
	
	// 후기His 추가
	@Override
	public ResultInfo registReviewHis(int intReviewNo) {
		
		ResultInfo objResult = new ResultInfo();
		
		try {
			int intRetVal = sqlSession.insert(MAPPER_PATH + "reviewHisRegist", intReviewNo);
			
			if (intRetVal != 1) {
				
				objResult.setIntRetVal(3220);
				objResult.setStrRetVal("TReviewHis 추가실패");
			}
			objResult.setIntRetVal(0);
			objResult.setStrRetVal("성공");
			
		} catch (Exception ex) {
			log.info("======[UserReviewDao][registReviewHis]======", intReviewNo);
			ex.printStackTrace();
			
			objResult.setIntRetVal(9999);
			objResult.setStrRetVal("TReviewHis 추가 에러");
		}
		return objResult;
	}
	
	// 후기 삭제
	@Override
	public ResultInfo deleteReview(int intReviewNo) {
		
		ResultInfo objRetVal = new ResultInfo();
		
		try {
			int intRetVal = sqlSession.delete(MAPPER_PATH + "reviewDelete", intReviewNo);
			
			if (intRetVal != 1) {
				objRetVal.setIntRetVal(3140);
				objRetVal.setStrRetVal("후기글 삭제 실패");
				
			} else {
    			objRetVal.setIntRetVal(0);
    			objRetVal.setStrRetVal("삭제 성공");
			}
		} catch (Exception ex) {
			log.info("======[UserReviewDao][deleteReview]======" + intReviewNo);
			ex.printStackTrace();
			
			objRetVal.setIntRetVal(9999);
			objRetVal.setStrRetVal("후기글 삭제 중 에러");
		}
		return objRetVal;
	}
	
	// 후기His 삭제
	@Override
	public ResultInfo deleteReviewHis(int intReviewNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			int intRetVal = sqlSession.delete(MAPPER_PATH + "reviewHisDelete", intReviewNo);
			
			if (intRetVal != 1) {
				objResultInfo.setIntRetVal(3340);
				objResultInfo.setStrRetVal("후기His 삭제 실패");
				
			} else {
    			objResultInfo.setIntRetVal(0);
    			objResultInfo.setStrRetVal("삭제 성공패");
			}
		} catch (Exception ex) {
			log.info("======[UserReviewDao][deleteReviewHis]======" + intReviewNo);
			
			objResultInfo.setIntRetVal(9999);
	        objResultInfo.setStrRetVal("후기His 삭제 중 에러");
		}
		return objResultInfo;
	}
	
	// 후기 리스트
	@Override
	public List<ReviewVo> getReviewList(Map<String, Object> reviewPagingMap) {
		
		List<ReviewVo> objReviewList = sqlSession.selectList(MAPPER_PATH + "reviewList", reviewPagingMap);
		
		return objReviewList;
	}
	
	// 총 후기글
	@Override
	public int getTotalReview(Map<String, Object> reviewPagingMap) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.selectOne(MAPPER_PATH + "getReviewTotalCount", reviewPagingMap);
			
		} catch (Exception ex) {
			log.info("======[UserReviewDao][getTotalReview]======");
			ex.printStackTrace();
			
			intRetVal = 0;
		}
		return intRetVal;
	}
	
	// 내 후기 리스트
	@Override
	public List<ReviewVo> getMyReviewList(Map<String, Object> reviewPagingMap) {
		
		List<ReviewVo> objReviewList = sqlSession.selectList(MAPPER_PATH + "myReviewList", reviewPagingMap);
		
		return objReviewList;
	}
	
	@Override
	public int getMyTotalReview(Map<String, Object> reviewPagingMap) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.selectOne(MAPPER_PATH + "getMyReviewTotalCount", reviewPagingMap);
			
		} catch (Exception ex) {
			log.info("======[UserReviewDao][getMyTotalReview]======");
			ex.printStackTrace();
			
			intRetVal = 0;
		}
		return intRetVal;
	}
	
	// 후기 상세내용
	@Override
	public ReviewVo reviewDetail(int intReviewNo) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "reviewDetail", intReviewNo);
			
		} catch (Exception ex) {
			ex.getMessage();
		}
		return null;
	}
	
	// 후기 수정
	@Override
	public Integer updateReview(ReviewVo objReview) {
		
		try {
			return sqlSession.update(MAPPER_PATH + "reviewUpdate", objReview);
			
		} catch (Exception ex) {
			log.info("=======[UserReviewDao][upDateReview]=======", objReview);
			ex.printStackTrace();
			
			return null;
		}
	}
	
	// 이미지 경로
	@Override
	public String getImgRootPath(int reviewNo) {
		
		try { 
			return sqlSession.selectOne(MAPPER_PATH + "getReviewImgRootPath", reviewNo);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][getImgRootPath]==========");
		}
		return null;
	}
	
	// 댓글 리스트
	@Override
	public List<ReviewCommentVo> getComment(ReviewCommentVo Comment) {
		
		try {
			List<ReviewCommentVo> objComment = sqlSession.selectList(MAPPER_PATH + "reviewCommentList", Comment);
			
			return objComment;
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][getComment]==========" + Comment);
		}
		return null;
	}
	
	// 댓글 작성
	@Override
	public int registComment(ReviewCommentVo objComment) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.insert(MAPPER_PATH + "registComment", objComment);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][registComment]========== : 댓글 작성 중 에러");
			intRetVal = 9999;
		}
		return intRetVal;
	}
	
	// 댓글 수정
	@Override
	public int updateComment(ReviewCommentVo objComment) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.update(MAPPER_PATH + "updateComment", objComment);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][registComment]========== : 댓글 작성 중 에러");
			intRetVal = 9999;
		}
		return intRetVal;
	}
	
	// 댓글 삭제
	@Override
	public int deleteComment(long lngCommentNo) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.delete(MAPPER_PATH + "deleteComment", lngCommentNo);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][deleteComment]========== : 댓글 삭제 중 에러");
		}
		return intRetVal;
	}
	
	// 조회수 증가
	@Override
	public int increaseView(int intReviewNo) {
		
		int intRetVal = 0;
		
		try {
			intRetVal = sqlSession.update(MAPPER_PATH + "increaseView", intReviewNo); 
			
		} catch (Exception ex) {
			ex.printStackTrace();
			log.info("==========[UserReviewDao][registComment]========== : 조회수 증가 중 에러");
			intRetVal = 9999;
		}
		return intRetVal;
	}
	
	// 후기 작성자 가져오기
	@Override
	public String getReviewWriter(int intReviewNo) {
	    
	    String strRetVal = null;
        
        try {
            strRetVal = sqlSession.selectOne(MAPPER_PATH + "getReviewWriter", intReviewNo); 
            
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("==========[UserReviewDao][getReviewWriter]==========");
            
            strRetVal = null;
        }
        return strRetVal;
	}
}
