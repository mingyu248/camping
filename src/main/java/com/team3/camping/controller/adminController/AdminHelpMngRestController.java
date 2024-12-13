package com.team3.camping.controller.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminHelpMngService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/admin")
public class AdminHelpMngRestController {

	@Autowired
	private AdminHelpMngService adminHelpMngService;
	
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
	
	// 문의 답변
	@PostMapping("/help_answer")
	public ResponseEntity<ResultInfo> submitHelpAnswer(HttpSession session, @RequestBody HelpVo objHelpVo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objHelpVo.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminHelpMngService.adminInsertHelpAnswer(objHelpVo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 문의 삭제
	@PostMapping("/help_delete")
	public ResponseEntity<ResultInfo> adminDeleteUserHelp(HttpSession session, @RequestBody HelpVo objHelpVo) {
	
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminHelpMngService.adminDeleteUserHelp(objHelpVo.getHelpNo());
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
}
