package com.team3.camping.controller.userController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.SuggestionVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserReservationService;
import com.team3.camping.service.userService.UserReviewService;
import com.team3.camping.service.userService.UserSuggestionService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserCampRestController {
    
    @Autowired
    private UserSuggestionService userSuggestionService;
    
    @Autowired
    private UserReservationService userReservationService;
    
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
        
        log.info("=====[loginInfo]===== : " + objLoginInfo);
        
        return objLoginInfo;
    }
    
    // 후기 등록
    @PostMapping("/review_write")
    public ResponseEntity<ResultInfo> reviewWrite(Model model, HttpSession session, @ModelAttribute ReviewVo objReview) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) { // 로그아웃 상태
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
            
            return ResponseEntity.ok(objResultInfo);
            
        } else if (this.getLoginInfo(session).getStateCode() != 0) { // 로그인 상태
            
            objReview.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
            objResultInfo = userReviewService.registReview(objReview);
        }
        model.addAttribute("userId", this.getLoginInfo(session).getUserName());

        return ResponseEntity.ok(objResultInfo);
    }
    
    
    
    
    
    // 캠핑장 추천
    @PostMapping("/suggestion")
    public ResponseEntity<ResultInfo> suggestion(HttpSession session, @RequestBody SuggestionVo objSuggestion) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objSuggestion.setUserKey(this.getLoginInfo(session).getUserKey());
        
        Integer intCartCheck = userSuggestionService.suggestionCheck(objSuggestion);
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else if (intCartCheck == null) {
            objResultInfo.setIntRetVal(5120);
            objResultInfo.setStrRetVal("캠핑장 추천중 에러 발생");
            
        } else if (intCartCheck != 0) {
            objResultInfo.setIntRetVal(5120);
            objResultInfo.setStrRetVal("이미 추천한한 캠핑장입니다");
            
        } else {
            objResultInfo = userSuggestionService.addSuggestion(objSuggestion);
        }
        
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 캠핑장 추천 취소
    @PostMapping("/suggestion_delete")
    public ResponseEntity<ResultInfo> suggestionDelete(HttpSession session, @RequestBody SuggestionVo objSuggestion) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objSuggestion.setUserKey(this.getLoginInfo(session).getUserKey());
        
        Integer intCartCheck = userSuggestionService.suggestionCheck(objSuggestion);
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else if (intCartCheck == null) {
            objResultInfo.setIntRetVal(5140);
            objResultInfo.setStrRetVal("캠핑장 추천중 에러 발생");
            
        } else if (intCartCheck == 0) {
            objResultInfo.setIntRetVal(5140);
            objResultInfo.setStrRetVal("이미 추천 취소한한 캠핑장입니다");
            
        } else {
            objResultInfo = userSuggestionService.suggestionDelete(objSuggestion);
        }
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 방 예약 상태 리스트
    @PostMapping("/get_reservation_available_room_list")
    @ResponseBody
    public ResponseEntity<List<ReservationVo>> getReservationAvailableRoomList(HttpSession session, @RequestBody ReservationVo res) {
        
        // 방 예약 상태 리스트
        List<ReservationVo> objReservationList = userReservationService.getReservationAvailableRoomList(res.getCampNo(), res.getCheckInDate(), res.getCheckOutDate());
        
        return ResponseEntity.ok(objReservationList);
    }
    
    // 캠핑장 예약
    @PostMapping("/reserve_camp")
    public ResponseEntity<ResultInfo> userCampReservation(HttpSession session, @RequestBody ReservationVo objReservation) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else {
            objReservation.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
            
            objResultInfo = userReservationService.userCampReservation(objReservation);
        }
        
        return ResponseEntity.ok(objResultInfo);
    }
}
