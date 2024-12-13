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
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminHelpMngService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminHelpMngController {
	
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
	
	// 문의 목록
	@GetMapping("/help_manage")
	public String getUserHelpList(Model model, HttpSession session,  @RequestParam(defaultValue = "1",  name = "page")       int     intPage
														            ,@RequestParam(defaultValue = "10", name = "limit")      int     intLimit
														            ,@RequestParam(required = false,    name = "title")      String  strHelpTitle
														            ,@RequestParam(required = false,    name = "id")         String  strUserId
														            ,@RequestParam(required = false,    name = "start_date") String  strStartDate
														            
														            ,@RequestParam(required = false,    name = "end_date")   String  strEndDate
														            ,@RequestParam(required = false,    name = "status")     Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			PagingUtil   objHelpList          = adminHelpMngService.getHelpList(strHelpTitle, strUserId, strStartDate, strEndDate, intStateCode
					                                                           ,intPage, intLimit);
			List<HelpVo> objHelpCountByStatus = adminHelpMngService.getHelpCountByStatus();
			
			model.addAttribute("helpList",    objHelpList.getObjHelpList());
			model.addAttribute("pagination",  objHelpList);
			model.addAttribute("helpTitle",   strHelpTitle);
			model.addAttribute("userId",      strUserId);
			model.addAttribute("startDate",   strStartDate);
			
			model.addAttribute("endDate",     strEndDate);
			model.addAttribute("stateCode",   intStateCode);
			model.addAttribute("statusCount", objHelpCountByStatus);
			model.addAttribute("adminId",     this.getLoginInfo(session).getUserName());
			
			return "admin/helpManage/helpManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 문의 상세
	@GetMapping("/help_detail")
	public String getUserHelpDetail(Model model, HttpSession session, @RequestParam(name= "no") int intHelpNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			HelpVo objHelpDetail = adminHelpMngService.getUserHelpDetail(intHelpNo);
			
			objHelpDetail.setHelpDetail(objHelpDetail.getHelpDetail().replaceAll("\n", "<br>"));

			model.addAttribute("adminId",     this.getLoginInfo(session).getUserName());
			model.addAttribute("helpDetail",  objHelpDetail);
			
			return "admin/helpManage/helpDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/helpManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
