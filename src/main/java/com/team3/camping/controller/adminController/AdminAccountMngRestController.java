package com.team3.camping.controller.adminController;

import java.util.Map;

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
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminAccountMngService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminAccountMngRestController {

	@Autowired
	private AdminAccountMngService adminMngService;

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
	
	// 관리자 아이디 중복확인
	@PostMapping("/id_check")
	public ResponseEntity<ResultInfo> adminIdDuplicateCheck(HttpSession session, @RequestParam("admin_id") String strAdminId) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminMngService.adminIdDuplicateCheck(strAdminId);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 관리자 계정생성
	@PostMapping("/create_account")
	public ResponseEntity<ResultInfo> createAdminAccount(HttpSession session, @RequestBody UserInfoVo objUserInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminMngService.createAdminAccount(objUserInfo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 관리자 계정 비활성화
	@PostMapping("/stop_account")
	public ResponseEntity<ResultInfo> stopAdminAccount(HttpSession session, @RequestBody Map<String, String> requestBody) {
		
		ResultInfo objResultInfo = new ResultInfo();

		if (this.getLoginInfo(session).getUserName() != null) {
			String strStopAdminId = requestBody.get("adminUserId");
			String strReason      = requestBody.get("reason");
			
			objResultInfo = adminMngService.stopAdminAccount(this.getLoginInfo(session).getUserName(), strStopAdminId, strReason);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 관리자 계정 활성화
	@PostMapping("/activate_account")
	public ResponseEntity<ResultInfo> activateAdminAccount(HttpSession session, @RequestBody Map<String, String> requestBody) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			String strActAdminId = requestBody.get("adminUserId");
			
			objResultInfo = adminMngService.activateAdminAccount(strActAdminId, strActAdminId);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 관리자 계정 삭제
	@PostMapping("/delete_account")
	public ResponseEntity<ResultInfo> deleteAdminAccount(HttpSession session, @RequestBody Map<String, String> requestBody) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			String strDelAdminId = requestBody.get("adminUserId");
			
			objResultInfo = adminMngService.deleteAdminAccount(strDelAdminId);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 관리자 계정 수정
	@PostMapping("/admin_update")
	public ResponseEntity<ResultInfo> updateAdminAccount(HttpSession session, @RequestBody UserInfoVo objAdminInfo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objAdminInfo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminMngService.updateAdminAccount(objAdminInfo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
}
