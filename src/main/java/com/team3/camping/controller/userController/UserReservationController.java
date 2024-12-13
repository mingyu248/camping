package com.team3.camping.controller.userController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserReservationService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserReservationController {
    
    @Autowired
    UserReservationService userReservationService;

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
    
    // 내 예약 리스트
    @GetMapping("/my_reservation")
    public String myReservation(Model model, HttpSession session, @RequestParam(defaultValue = "1", name = "page")   int intPage
                                                                 ,@RequestParam(defaultValue = "10", name = "limit") int intLimit) {
        
        ResultInfo          objResultInfo        = new ResultInfo();
        List<ReservationVo> getReservationList   = new ArrayList<>();
        Map<String, Object> reservationPagingMap = new HashMap<>();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            reservationPagingMap.put("currentPage",   intPage);
            reservationPagingMap.put("limit",         intLimit);
            reservationPagingMap.put("userIdOrEmail", this.getLoginInfo(session).getUserKey());
            
            getReservationList = userReservationService.getMyReservationList(reservationPagingMap);
            
            model.addAttribute("myReservationList", getReservationList);
            model.addAttribute("searchOption",      reservationPagingMap);
            model.addAttribute("userId",            this.getLoginInfo(session).getUserName());
           
            return "user/myPage/myReservation";
        }
        
    }
    
    // 상세 페이지로 이동
    @GetMapping("/reservation_detail")
    public String reservationDetail(Model model, HttpSession session, @RequestParam("reservation_no") int intReservationNo) {
        
        ResultInfo    objResultInfo        = new ResultInfo();
        ReservationVo objReservation       = new ReservationVo();
        String        strReservationWriter = userReservationService.getReservationWriter(intReservationNo);
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else if (strReservationWriter == null) {
            objResultInfo.setIntRetVal(8110);
            objResultInfo.setStrRetVal("작성자 가져오는 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else if (!strReservationWriter.equals(this.getLoginInfo(session).getUserKey())) {
            objResultInfo.setIntRetVal(8110);
            objResultInfo.setStrRetVal("나의 예약만 확인할 수 있습니다");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            objReservation = userReservationService.getReservationDetail(intReservationNo);
            
            if (objReservation == null) {
                
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("예약 정보 가져오는 중 에러");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "user/error";
                
            } else {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
                
                model.addAttribute("resultInfo",  objResultInfo);
                model.addAttribute("reservation", objReservation);
                model.addAttribute("userId",      this.getLoginInfo(session).getUserName());
                
                return "user/reservation/reservationDetail";
            }
        }
    }
}