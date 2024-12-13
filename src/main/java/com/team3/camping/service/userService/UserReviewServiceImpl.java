package com.team3.camping.service.userService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.multipart.MultipartFile;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.repository.userRepository.UserReviewDao;

@Service
public class UserReviewServiceImpl implements UserReviewService {
	
	private static final String REVIEW_IMAGE_FOLDER_PATH  = "Z:/Develope/Works/Java/SpringBoot/prj_camping/src/main/resources/static/images/user/review/";
    //private static final String REVIEW_IMAGE_FOLDER_PATH  = "E:/khh/works/spring/prj_camping/src/main/resources/static/images/user/review/";
    private static final String REVIEW_IMAGE_PROJECT_PATH = "/camping/images/user/review/";
	    
	@Autowired
	private UserReviewDao userReviewDao;
	
	private final PlatformTransactionManager transactionManager;
    
    public UserReviewServiceImpl(PlatformTransactionManager transactionManager) {
          
        this.transactionManager = transactionManager;
    }
    
	// 후기 등록
	@Override
	public ResultInfo registReview(ReviewVo objReview) {
		
		ResultInfo objRetVal = new ResultInfo();
		// 시퀀스 넘버 가져오기
		int reviewNo = userReviewDao.getSequence();
		objReview.setReviewNo(reviewNo);
		
		UUID uuid = UUID.randomUUID();
		
		// 원인 : null 에서 getOriginFilename 해서 에러뜸
		// 해결 : null 이면 그냥 실행X
		
		if (objReview.getFile() != null) {
			
			String strProjectImgFilePath = REVIEW_IMAGE_PROJECT_PATH + uuid + "_" + objReview.getFile().getOriginalFilename(); // 프로젝트 내 경로
			String strRootImgFilePath    = REVIEW_IMAGE_FOLDER_PATH  + uuid + "_" + objReview.getFile().getOriginalFilename(); // 로컬 절대 경로
			
			objReview.setReviewImgPath(strProjectImgFilePath);
			objReview.setReviewImgRootPath(strRootImgFilePath);
			
			// 이미지 파일
			MultipartFile objMultiPartFile = objReview.getFile();
			// 파일 객체 생성
			File objFile = new File(objReview.getReviewImgRootPath());
			
			try {
				objMultiPartFile.transferTo(objFile);
				
			} catch (IllegalStateException illegalEx) {
				illegalEx.printStackTrace();
				
			} catch (IOException ioEx) {
				ioEx.printStackTrace();
				
				return objRetVal;
			}
		} 
        
		int intRetVal = userReviewDao.registReview(objReview);
		
		if (intRetVal != 1) {
			objRetVal.setIntRetVal(3120);
			objRetVal.setStrRetVal("후기 작성에 실패했습니다.");
			
			return objRetVal;
		}
		
		ResultInfo reviewHis = userReviewDao.registReviewHis(reviewNo); 
		
		if (reviewHis.getIntRetVal() != 0) {
			objRetVal.setIntRetVal(3320);
			objRetVal.setStrRetVal("후기 상태코드 저장 실패");
			
			return objRetVal;
		}
		
        objRetVal.setIntRetVal(0);
        objRetVal.setIntReviewNo(reviewNo);
        objRetVal.setIntCampNo(objReview.getCampNo());
        
		return objRetVal;
	}
	
	// 후기 삭제
	@Override
	public ResultInfo deleteReview(int intReviewNo) {
		
		TransactionStatus txStatusDeleteReview = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		ResultInfo objRetVal = new ResultInfo();
		
		try {
			String strImgRootPath = userReviewDao.reviewDetail(intReviewNo).getReviewImgRootPath();
			// DB 데이터 삭제
			ResultInfo reviewDelete    = userReviewDao.deleteReview(intReviewNo);
			ResultInfo reviewHisDelete = userReviewDao.deleteReviewHis(intReviewNo);
			
			if (reviewDelete.getIntRetVal() == 0 && reviewHisDelete.getIntRetVal() == 0) {
				
				// 이미지 삭제 (이미지 경로를 DB데이터에서 가져옴)
				if (strImgRootPath != null && !strImgRootPath.isEmpty()) {
	                File file = new File(strImgRootPath);
	                file.delete();
	            }
				transactionManager.commit(txStatusDeleteReview);
				
		        objRetVal.setIntRetVal(0);
		        objRetVal.setStrRetVal("삭제 성공");
	            
			} else {
				transactionManager.rollback(txStatusDeleteReview);
				
				objRetVal.setIntRetVal(9999);
				objRetVal.setStrRetVal("삭제 실패");
			}
		} catch (Exception ex) {
			transactionManager.rollback(txStatusDeleteReview);
			ex.printStackTrace();
			
			objRetVal.setIntRetVal(9999);
			objRetVal.setStrRetVal("삭제 중 에러");
		}
		return objRetVal;
	}
	
	// 후기 리스트
	@Override
	public List<ReviewVo> getReviewList(Map<String, Object> reviewPagingMap) {
		
		PagingUtil pagingUtil = new PagingUtil();
		
        pagingUtil.setCurrentPage((int)reviewPagingMap.get("currentPage"));
        pagingUtil.setLimit((int)reviewPagingMap.get("limit"));
        
        reviewPagingMap.put("startRow", pagingUtil.getStartRow());
        reviewPagingMap.put("endRow",   pagingUtil.getEndRow());
		
        List<ReviewVo> objReviewList = userReviewDao.getReviewList(reviewPagingMap);
		
		int intTotalReview = userReviewDao.getTotalReview(reviewPagingMap);
		
		pagingUtil.setTotalPages(intTotalReview);
		pagingUtil.calculateTotalPages(intTotalReview);
		
		reviewPagingMap.put("totalPages",   pagingUtil.getTotalPages());
		reviewPagingMap.put("pageRange",   pagingUtil.getPageRange());

		return objReviewList;
	}
	
	// 내 후기 리스트
	@Override
	public List<ReviewVo> getMyReviewList(Map<String, Object> reviewPagingMap) {
		
		PagingUtil pagingUtil = new PagingUtil();
		
        pagingUtil.setCurrentPage((int)reviewPagingMap.get("currentPage"));
        pagingUtil.setLimit((int)reviewPagingMap.get("limit"));
        
        reviewPagingMap.put("startRow", pagingUtil.getStartRow());
        reviewPagingMap.put("endRow",   pagingUtil.getEndRow());
		
        List<ReviewVo> objReviewList  = userReviewDao.getMyReviewList(reviewPagingMap);
		int            intTotalReview = userReviewDao.getMyTotalReview(reviewPagingMap);
		
		pagingUtil.setTotalPages(intTotalReview);
		pagingUtil.calculateTotalPages(intTotalReview);
		
		reviewPagingMap.put("totalPages", pagingUtil.getTotalPages());
		reviewPagingMap.put("pageRange",  pagingUtil.getPageRange());

		return objReviewList;
	}
	
	// 후기 상세내용
	@Override
	public ReviewVo reviewDetail(int intReviewNo) {
		
		userReviewDao.increaseView(intReviewNo);
		
		return userReviewDao.reviewDetail(intReviewNo);
	}
	
	// 후기 수정
	@Override
	public ResultInfo reviewUpdate(ReviewVo objReview) {
		
		ResultInfo objRetVal = new ResultInfo();
		Integer    intRetVal = null;
		try {
			// DB에 있는 이미지 경로 가져오기
			String strOriginImgRootPath = userReviewDao.getImgRootPath(objReview.getReviewNo());
			
			// DB
			// DB에 저장할 데이터 가공, 파일이 없으면 경로는 null
			// 파일이 없을 떄 getOriginalFilename 하면 에러
			if (objReview.getFile() != null) {
				UUID uuid = UUID.randomUUID();
				
				String strProjectImgFilePath = REVIEW_IMAGE_PROJECT_PATH + uuid + "_" + objReview.getFile().getOriginalFilename(); // 프로젝트 내 경로
				String strRootImgFilePath    = REVIEW_IMAGE_FOLDER_PATH  + uuid + "_" + objReview.getFile().getOriginalFilename(); // 로컬 절대 경로
				
				objReview.setReviewImgPath(strProjectImgFilePath);
				objReview.setReviewImgRootPath(strRootImgFilePath);
			} 
			
			// DB 수정
			intRetVal = userReviewDao.updateReview(objReview);
			
			if (intRetVal == null) {
				objRetVal.setIntRetVal(3130);
				objRetVal.setStrRetVal("이미지 파일 저장 중 오류가 발생했습니다.");
				
	            return objRetVal; 
			}
			MultipartFile objMultiPartFile = objReview.getFile();
			
			// 파일
			if (objReview.getImgState() == 1) { // 이미지 변경사항 없음
				
			} else if (objReview.getImgState() == 2) { // 이미지 변경(삭제 후 추가)
				
				// 기존 파일이 있으면(null이 아니라는 건 기존 파일이 있다는 뜻)
				if (strOriginImgRootPath != null) {
	                File file = new File(strOriginImgRootPath);
	                file.delete();
	            }
			    File objFile = new File(objReview.getReviewImgRootPath()); 
				 
				objMultiPartFile.transferTo(objFile);
				
			} else if (objReview.getImgState() == 3) { // 이미지 삭제
				if (strOriginImgRootPath != null) {
					File file = new File(strOriginImgRootPath);
					file.delete();
				}
			} else {
				objRetVal.setIntRetVal(3130);
				objRetVal.setStrRetVal("이미지 상태코드 전달 실패");
				
				return objRetVal;
			}
			
		} catch (Exception e) {
			objRetVal.setIntRetVal(3130);
			objRetVal.setStrRetVal("service 에러");
			
			return objRetVal;
		}
		
		objRetVal.setIntRetVal(0);
		objRetVal.setStrRetVal("성공");
		objRetVal.setIntReviewNo(objReview.getReviewNo());
		objRetVal.setIntCampNo(objReview.getCampNo());
		
		return objRetVal;
	}
	
	// 댓글 리스트
	@Override
	public List<ReviewCommentVo> getComment(ReviewCommentVo objComment) {
		
		return userReviewDao.getComment(objComment);
	}
	
	// 댓글 작성
	@Override
	public ResultInfo registComment(ReviewCommentVo objComment) {
		
		ResultInfo objRetVal = new ResultInfo();
		
		int intRetVal = userReviewDao.registComment(objComment);
		
		if (intRetVal != 1) {
			objRetVal.setIntRetVal(3220);
			objRetVal.setStrRetVal("댓글 작성 실패");
		}
		return objRetVal;
	}
	
	// 댓글 수정
	@Override
	public ResultInfo updateComment(ReviewCommentVo objComment) {
		
		ResultInfo objRetVal = new ResultInfo();
		
		int intRetVal = userReviewDao.updateComment(objComment);
		
		if (intRetVal != 1) {
			objRetVal.setIntRetVal(3230);
			objRetVal.setStrRetVal("댓글 수정 실패");
		}		
		return objRetVal;
	}
	
	// 댓글 삭제
	@Override
	public ResultInfo deleteComment(long lngComment) {
		
		ResultInfo objRetVal = new ResultInfo();
		
		int intRetVal = userReviewDao.deleteComment(lngComment);
		
		if (intRetVal != 1) {
			objRetVal.setIntRetVal(3240);
			objRetVal.setStrRetVal("댓글 삭제 실패");
		}
		return objRetVal;
	}
	
	// 후기 작성자 가져오기
	@Override
	public String getReviewWriter(int intReviewNo) {
	    
	    return userReviewDao.getReviewWriter(intReviewNo);
	}
	
}
