package com.team3.camping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.ResultInfo;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	// 로그인 페이지
	@GetMapping("/account")
	public String login() {
		
		return "login";
	}
	
	// 로그인 에러
    @GetMapping("/login_fail")
    public String loginFail(Model model, HttpSession session) {
        
        log.info("===========[LoginController][loginError]===========");
        
        ResultInfo objResultInfo = new ResultInfo();
        Exception exception = (Exception)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        
        objResultInfo.setIntRetVal(9999);
        objResultInfo.setStrRetVal(exception.getMessage());
        objResultInfo.setStrRedirectUrl("/camping/account");
        
        model.addAttribute("resultInfo", objResultInfo);
        
        return "loginFail";
    }
    
    // 로그인 에러 (소셜)
    @GetMapping("/login_fail_social")
    public String loginFailSocial(Model model, @RequestParam("errorMessage") String strErrorMessage) {
        
        log.info("===========[LoginController][loginFailSocial]===========");
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objResultInfo.setIntRetVal(9999);
        objResultInfo.setStrRetVal(strErrorMessage);
        objResultInfo.setStrRedirectUrl("/camping/account");
        
        model.addAttribute("resultInfo", objResultInfo);
        
        return "loginFail";
    }
    
    // 페이지 접근 제한
    @GetMapping("/page_access_denied")
    public String pageAccessDenied(Model model) {
        
        log.info("===========[LoginController][pageAccessDenied]===========");
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objResultInfo.setIntRetVal(9999);
        objResultInfo.setStrRetVal("페이지 접근 권한이 없습니다");
        objResultInfo.setStrRedirectUrl("/camping/main");
        
        model.addAttribute("resultInfo", objResultInfo);
        
        return "accessDenied";
    }
}
