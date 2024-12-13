package com.team3.camping.service.userService;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;

public interface UserReviewService {
    
	// 후기 등록
	public ResultInfo registReview(ReviewVo objReview);
	// 후기 삭제
	public ResultInfo deleteReview(int intReviewNo);
	// 후기 리스트
	public List<ReviewVo> getReviewList(Map<String, Object> reviewPagingMap);
	// 내 후기 리스트
	public List<ReviewVo> getMyReviewList(Map<String, Object> reviewPagingMap);
	// 후기 상세내용
	public ReviewVo reviewDetail(int intReviewNo);
	
	
	// 후기 수정
	public ResultInfo reviewUpdate(ReviewVo objReview);
	// 댓글 리스트
	public List<ReviewCommentVo> getComment(ReviewCommentVo objComment);
	// 댓글 작성
	public ResultInfo registComment(ReviewCommentVo objComment);
	// 댓글 수정
	public ResultInfo updateComment(ReviewCommentVo objComment);
	// 댓글 삭제 
	public ResultInfo deleteComment(long lngComment);
	
	// 후기 작성자 가져오기
    public String getReviewWriter(int intReviewNo);
}
