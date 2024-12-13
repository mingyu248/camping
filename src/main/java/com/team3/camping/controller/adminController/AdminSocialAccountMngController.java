package com.team3.camping.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.domain.naverAndGoogle.User;
import com.team3.camping.service.adminService.AdminSocialAccountMngService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminSocialAccountMngController {
	
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
	
	// 소셜 회원 목록
	@GetMapping("/social_manage")
	public String getSocialUserList(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")       int     intPage
	           														 ,@RequestParam(defaultValue = "10", name = "limit")      int     intLimit
	           														 ,@RequestParam(required = false,    name = "id")         String  strUserEmailOrId
	           														 ,@RequestParam(required = false,    name = "platform")   String  strPlatform
	           														 ,@RequestParam(required = false,    name = "start_date") String  strStartDate
	           														 
	          														 ,@RequestParam(required = false,    name = "end_date")   String  strEndDate
	           														 ,@RequestParam(required = false,    name = "status")     Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objSocialStatusCount = adminSocialAccountMngService.getSocialCountByStatus();
			List<User>       objSocialPlatform    = adminSocialAccountMngService.getSocialAccountPlatform();
			PagingUtil       objSocialAccountList = adminSocialAccountMngService.getSocialAccountList(strUserEmailOrId, strPlatform, strStartDate, strEndDate, intStateCode, intPage, intLimit);
			
			model.addAttribute("statusCount",       objSocialStatusCount);
			model.addAttribute("platformList",      objSocialPlatform);
			model.addAttribute("pagination",        objSocialAccountList);
			model.addAttribute("socialAccountList", objSocialAccountList.getObjUserList());
			model.addAttribute("userEmailOrId",     strUserEmailOrId);
			
			model.addAttribute("platform",          strPlatform);
			model.addAttribute("userEamilOrId",     strUserEmailOrId);
			model.addAttribute("startDate",         strStartDate);
			model.addAttribute("endDate",           strEndDate);
			model.addAttribute("stateCode",         intStateCode);
			
			model.addAttribute("adminId",           this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/socialManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 소셜 회원 상세정보
	@GetMapping("/social_detail")
	public String getSocialAccountDetail(Model model, HttpSession session ,@RequestParam(name = "no") int intUserNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			UserInfoVo objSocialDetail = adminSocialAccountMngService.getSocialAccountDetail(intUserNo);
			
			model.addAttribute("socialDetail", objSocialDetail);
			model.addAttribute("adminId",      this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/socialDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/socialManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 소셜 회원 상세내역
	@GetMapping("/social_history")
	public String getSocialAccountHistory(Model model, HttpSession session ,@RequestParam(name = "no") int intUserNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objSocialHistoryList = adminSocialAccountMngService.getSocialAccountHistory(intUserNo);
			
			model.addAttribute("userNo",        intUserNo);
			model.addAttribute("socialHistory", objSocialHistoryList);
			model.addAttribute("adminId",       this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/socialHistory";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
