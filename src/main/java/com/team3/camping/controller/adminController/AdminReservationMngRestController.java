package com.team3.camping.controller.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminReservationMngService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminReservationMngRestController {

	@Autowired
	private AdminReservationMngService adminReservationMngService;
	
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
	
	// 캠핑장 예약 보류
	@PostMapping("hold_reservation")
	public ResponseEntity<ResultInfo> holdCampReservation(HttpSession session, @RequestBody ReservationVo objReservationVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objReservationVo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminReservationMngService.holdCampReservation(objReservationVo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 캠핑장 예약 활성화
	@PostMapping("/activate_reservation")
	public ResponseEntity<ResultInfo> activateCampReservation(HttpSession session, @RequestBody ReservationVo objReservationVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objReservationVo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminReservationMngService.activateReservation(objReservationVo);
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 캠핑장 예약정보 제거
	@PostMapping("/delete_reservation")
	public ResponseEntity<ResultInfo> deleteReservation(HttpSession session, @RequestBody ReservationVo objReservationVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminReservationMngService.deleteReservation(objReservationVo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
}
