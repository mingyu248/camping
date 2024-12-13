package com.team3.camping.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserReviewService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserReviewRestController {
	
	@Autowired
	private UserReviewService userReviewService;
    
    // 현재 로그인 정보 반환(FK로 사용할 userKey, 이름 표시를 위한 userName, 로그인 상태 판별을 위한 stateCode)
    private LoginInfoVo getLoginInfo(HttpSession session) {
        
        // 자체 로그인 정보, 로그인 안한 상태일 경우 principal 은 anonymousUser
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        // 소셜 로그인 정보, 로그인 안한 상태일 경우 socialUser 은 null
        Object socialUser = session.getAttribute("user");
        
        LoginInfoVo objLoginInfo = new LoginInfoVo(); // 로그아웃 상태인 경우 stateCode: 0
        
        if (principal instanceof CustomUser) {        // 자체 로그인 상태인 경우 stateCode: 1
            
            CustomUser objCustomUser = (CustomUser)principal;
            
            objLoginInfo.setStateCode(1);
            objLoginInfo.setUserKey(objCustomUser.getUserId());
            objLoginInfo.setUserName(objCustomUser.getUsername());
            
        } else if (socialUser != null) {              // 소셜 로그인 상태인 경우 stateCode: 2
            
            SessionUser objSessionUser = (SessionUser)socialUser;
            
            objLoginInfo.setStateCode(2);
            objLoginInfo.setUserKey(objSessionUser.getEmail());
            objLoginInfo.setUserName(objSessionUser.getName());
        }
        return objLoginInfo;
    }

    // 후기 삭제
    @PostMapping("/review_delete")
    public ResponseEntity<ResultInfo> reviewDelete(HttpSession session, @RequestBody ReviewVo objReview) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
            
            return ResponseEntity.ok(objResultInfo);
            
        } else {
            objResultInfo = userReviewService.deleteReview(objReview.getReviewNo());
            objResultInfo.setStrPageInfo(objReview.getPageInfo());
        }
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 후기 수정
    @PostMapping("/review_update")
    public ResponseEntity<ResultInfo> reviewUpdate(HttpSession session, @ModelAttribute ReviewVo objReview) {
        
        ResultInfo objResultInfo = new ResultInfo();

        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
            
            return ResponseEntity.ok(objResultInfo);
            
        } else {
            objResultInfo = userReviewService.reviewUpdate(objReview);
            objResultInfo.setStrPageInfo(objReview.getPageInfo());
        }
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 댓글 작성
    @PostMapping("/comment_regist")
    public ResponseEntity<ResultInfo> registComment(HttpSession session, @RequestBody ReviewCommentVo objComment) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
            
        } else {
            objComment.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
            objResultInfo = userReviewService.registComment(objComment);
            
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("댓글 작성 성공");
        }
        return  ResponseEntity.ok(objResultInfo);
    }
    
    // 댓글 수정
    @PostMapping("/comment_update")
    public ResponseEntity<ResultInfo> updateComment(HttpSession session, @RequestBody ReviewCommentVo objComment) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            if (this.getLoginInfo(session).getStateCode() == 0) {
                objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
               
            } else {
                objComment.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
                objResultInfo = userReviewService.updateComment(objComment);
            }
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("댓글 수정 성공");
            return ResponseEntity.ok(objResultInfo);
            
        } catch (Exception ex) {
            ex.getMessage();
            
            objResultInfo.setStrRetVal("컨트롤러 에러");
            return ResponseEntity.ok(objResultInfo);    
        }
    }
    
    // 댓글 삭제
    @PostMapping("/comment_delete")
    public ResponseEntity<ResultInfo> deleteComment(HttpSession session, @RequestBody long lngCommentNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            if (this.getLoginInfo(session).getStateCode() == 0) {
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
                
            } else {
                objResultInfo = userReviewService.deleteComment(lngCommentNo);
            }
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("댓글 삭제 성공");
            
            return ResponseEntity.ok(objResultInfo);
            
        } catch (Exception ex) {
            ex.getMessage();
        }
        objResultInfo.setStrRetVal("컨트롤러 에러");
        
        return ResponseEntity.ok(objResultInfo);
    }
}