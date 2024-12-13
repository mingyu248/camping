package com.team3.camping.service.adminService;

import java.util.List;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;

public interface AdminReviewMngService {

	// 후기 정지
	public ResultInfo userCampReviewDeactivate(ReviewVo objReviewVo);
	// 후기 활성화
	public ResultInfo userReviewActivate(ReviewVo objReviewVo);
	// 후기 삭제
	public ResultInfo userReviewDelete(ReviewVo objReview);
	// 캠핑장 목록
	public PagingUtil getCampReivewList(int intCampNo, String strReivewTitle, String strUserAccount, String strStartDate, String strEndDate
                                       ,Integer intStateCode ,int intPage, int intLimit);
	// 캠핑장 후기 상세정보
	public ReviewVo getCampReviewDetail(int intReviewNo);
	
	
	// 캠핑장 후기 상세내역
	public List<ReviewVo> getCampReviewHistory(int intReviewNo);
	// 캠핑장 후기 댓글
	public List<ReviewCommentVo> getCampReviewCommentList(int intReviewNo);
	// 캠핑장 후기 댓글 갯수
	public int getCampReviewCommentCount(int intReviewNo);
	// 댓글 정지
	public ResultInfo userCommentDeactivate(int intCommentNo);
	// 댓글 활성화
	public ResultInfo userCommentActivate(int intCommentNo);
	
	
	// 댓글 삭제
	public ResultInfo userCommentDelete(int intCommentNo);
}
