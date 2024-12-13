package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;

public interface UserReviewDao {
	
	// 후기 작성
	public int registReview(ReviewVo objReview);
	// 후기 상태코드 저장
	public ResultInfo registReviewHis(int intReviewNo);
	// 시퀀스 번호 가져오기
	public int getSequence();
	// 전체 리스트
	public List<ReviewVo> getReviewList(Map<String, Object> reviewPagingMap);
	// 총 후기글
	public int getTotalReview(Map<String, Object> reviewPagingMap);
	
	
	// 리뷰 갯수
	public int getMyTotalReview(Map<String, Object> reviewPagingMap);
	// 내 후기글 리스트
	public List<ReviewVo> getMyReviewList(Map<String, Object> reviewPagingMap);
	// 후기 상세내용
	public ReviewVo reviewDetail(int intReviewNo);
	// 후기 삭제
	public ResultInfo deleteReview(int intReviewNo);
	// 후기 His 삭제
	public ResultInfo deleteReviewHis(int intReviewNo);
	
	
	// 후기 수정
	public Integer updateReview(ReviewVo objReview);
	// 이미지 경로
	public String getImgRootPath(int reviewNo);
	// 댓글 리스트
	public List<ReviewCommentVo> getComment(ReviewCommentVo objComment);
	// 댓글 작성
	public int registComment(ReviewCommentVo objComment);
	// 댓글 수정
	public int updateComment(ReviewCommentVo objComment);
	
	
	// 댓글 삭제
	public int deleteComment(long lngCommentNo);
	// 조회수 증가
	public int increaseView(int intReviewNo);
	// 후기 작성자 가져오기
    public String getReviewWriter(int intReviewNo);

    
}
