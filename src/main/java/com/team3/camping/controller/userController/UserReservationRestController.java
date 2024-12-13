package com.team3.camping.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminReservationMngService;
import com.team3.camping.service.userService.UserReservationService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserReservationRestController {
    
    @Autowired
    private AdminReservationMngService adminReservationMngService;
    
    @Autowired
    private UserReservationService userReservationService;
    
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
    
    // 캠핑장 예약정보 제거
    @PostMapping("/delete_reservation")
    public ResponseEntity<ResultInfo> deleteReservation(HttpSession session, @RequestBody ReservationVo objReservationVo) {
        
        ResultInfo    objResultInfo        = new ResultInfo();
        String        strReservationWriter = userReservationService.getReservationWriter(objReservationVo.getReservationNo());
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else if (strReservationWriter == null) {
            objResultInfo.setIntRetVal(8110);
            objResultInfo.setStrRetVal("작성자 확인 중 에러");
            
        } else if (!strReservationWriter.equals(this.getLoginInfo(session).getUserKey())) {
            objResultInfo.setIntRetVal(8110);
            objResultInfo.setStrRetVal("나의 예약만 삭제할 수 있습니다");
            
        } else {
            objResultInfo = adminReservationMngService.deleteReservation(objReservationVo);
        }
        return ResponseEntity.ok(objResultInfo);
    }
}
