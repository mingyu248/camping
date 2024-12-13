package com.team3.camping.service.adminService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.repository.adminRepository.AdminReviewMngDao;

@Service
public class AdminReviewMngServiceImpl implements AdminReviewMngService {

	@Autowired
	private AdminReviewMngDao adminReviewMngDao;
	
	// 후기 정지
	@Override
	public ResultInfo userCampReviewDeactivate(ReviewVo objReviewVo) {

		return adminReviewMngDao.userCampReviewDeactivate(objReviewVo);
	}

	// 후기 활성화
	@Override
	public ResultInfo userReviewActivate(ReviewVo objReviewVo) {

		return adminReviewMngDao.userReviewActivate(objReviewVo);
	}

	// 후기 삭제
	@Override
	public ResultInfo userReviewDelete(ReviewVo objReview) {

		return adminReviewMngDao.userReviewDelete(objReview);
	}

	// 캠핑장 목록
	@Override
	public PagingUtil getCampReivewList(int intCampNo, String strReivewTitle, String strUserAccount, String strStartDate, String strEndDate
			                           ,Integer intStateCode ,int intPage, int intLimit) {
		
		PagingUtil objPaginUtil = new PagingUtil();
		
		objPaginUtil.setCurrentPage(intPage);
		objPaginUtil.setLimit(intLimit);
		
		try {
			List<ReviewVo> objCampReivewList = adminReviewMngDao.getCampReivewList(intCampNo, strReivewTitle, strUserAccount, strStartDate, strEndDate
					                                                              ,intStateCode ,objPaginUtil.getStartRow(), objPaginUtil.getEndRow());
			int intCampReviewTotalCount      = adminReviewMngDao.getCampReivewTotalCount(intCampNo, strReivewTitle, strUserAccount, strStartDate, strEndDate
					                                                                    ,intStateCode);
			
			objPaginUtil.setTotalCount(intCampReviewTotalCount);
			objPaginUtil.calculateTotalPages(intCampReviewTotalCount);
			objPaginUtil.setObjReviewList(objCampReivewList);
			
			objPaginUtil.setPageRange(objPaginUtil.getPageRange());
			
		} catch (Exception ex) {
			// TODO: handle exception
		}
		return objPaginUtil;
	}
	
	// 캠핑장 후기 상세정보
	@Override
	public ReviewVo getCampReviewDetail(int intReviewNo) {
		
		return adminReviewMngDao.getCampReviewDetail(intReviewNo);
	}

	// 캠핑장 상세내역
	@Override
	public List<ReviewVo> getCampReviewHistory(int intReviewNo) {
		
		return adminReviewMngDao.getCampReviewHistory(intReviewNo);
	}

	// 캠핑장 후기 댓글
	@Override
	public List<ReviewCommentVo> getCampReviewCommentList(int intReviewNo) {

		return adminReviewMngDao.getCampReviewCommentList(intReviewNo);
	}

	// 캠핑장 후기 댓글 갯수
	@Override
	public int getCampReviewCommentCount(int intReviewNo) {

		return adminReviewMngDao.getCampReviewCommentCount(intReviewNo);
	}

	// 댓글 정지
	@Override
	public ResultInfo userCommentDeactivate(int intCommentNo) {

		return adminReviewMngDao.userCommentDeactivate(intCommentNo);
	}

	// 댓글 활성화
	@Override
	public ResultInfo userCommentActivate(int intCommentNo) {

		return adminReviewMngDao.userCommentActivate(intCommentNo);
	}

	// 댓글 삭제
	@Override
	public ResultInfo userCommentDelete(int intCommentNo) {

		return adminReviewMngDao.userCommentDelete(intCommentNo);
	}
}