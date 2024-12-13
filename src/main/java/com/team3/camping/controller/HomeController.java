package com.team3.camping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.LoginService;
import com.team3.camping.service.userService.UserCampService;
import com.team3.camping.service.adminService.AdminStatisticsMngService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private UserCampService userCampService;
    
	@Autowired
	private AdminStatisticsMngService adminStatisticsMngService;
	
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
	
	// 메인페이지
	@GetMapping({"/", "/main"})
	public String main(Model model, HttpSession session) {
		
	    LoginInfoVo objLoginInfo = this.getLoginInfo(session);

	    String strRetVal = loginService.getMainUrl();
	    
	    if (strRetVal.equals("main")) {
	        
	        model.addAttribute("popularCampList", userCampService.getPopularCampList());
	        
	    } else if (strRetVal.equals("admin/adminMain")) {
	    	List<ReservationVo> reservationStatisticsByThirtyDays = adminStatisticsMngService.getReservationStatisticsByThiryDays();
			List<UserInfoVo>    userStatisticsByThirtyDays        = adminStatisticsMngService.getUserStatisticsByThirtyDays();
			List<UserInfoVo>    socialStatisticsByThirtyDays      = adminStatisticsMngService.getSocialStatisticsByThirtyDays();
			
			model.addAttribute("reservationStatistics", reservationStatisticsByThirtyDays);
			model.addAttribute("userStatistics",        userStatisticsByThirtyDays);
			model.addAttribute("socialStatistics",      socialStatisticsByThirtyDays);
		}
	    model.addAttribute("userId", objLoginInfo.getUserName());
		
	    return strRetVal;
	}
	
	// 인덱스
	@GetMapping("/index")
	public String index() {
		
		return "index";
	}
	
	// 관리자 에러
	@GetMapping("/admin/error") 
	public String errorPage() {
		
		return "admin/error";
	}
}
