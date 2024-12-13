package com.team3.camping.controller.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminSocialAccountMngService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminSocialAccountMngRestController {

	@Autowired
	private AdminSocialAccountMngService adminSocialAccountMngService;
	
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
	
	// 소셜 계정 비활성화
	@PostMapping("/stop_social")
	public ResponseEntity<ResultInfo> stopSocialAccount(HttpSession session, @RequestBody UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objUserInfo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminSocialAccountMngService.stopSocialAccount(objUserInfo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 소셜 계정 활성화
	@PostMapping("/activate_social")
	public ResponseEntity<ResultInfo> activateSocialAccount(HttpSession session, @RequestBody UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objUserInfo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminSocialAccountMngService.activateSocialAccount(objUserInfo);
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 소셜 계정 삭제
	@PostMapping("/delete_social")
	public ResponseEntity<ResultInfo> deleteSocialAccount(HttpSession session, @RequestBody UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() == null) {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용 가능합니다.");
			
		} else {
			objResultInfo = adminSocialAccountMngService.deleteSocialAccount(objUserInfo);
		}
		return ResponseEntity.ok(objResultInfo);
	}
}
