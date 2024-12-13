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
import com.team3.camping.service.adminService.AdminAccountMngService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminUserMngController {

	@Autowired
	private AdminAccountMngService adminAccountMngService;
	
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
	
	// 회원 목록
	@GetMapping("/user_manage")
	public String getUserList(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")       int     intPage
													           ,@RequestParam(defaultValue = "10", name = "limit")      int     intLimit
													           ,@RequestParam(required = false,    name = "id")         String  strUserId
													           ,@RequestParam(required = false,    name = "start_date") String  strStartDate
													           ,@RequestParam(required = false,    name = "end_date")   String  strEndDate
													            
													           ,@RequestParam(required = false,    name = "status")     Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			PagingUtil       objUserList        = adminAccountMngService.getAdminOrUserList(strUserId, strStartDate, strEndDate, intStateCode, intPage
					                                                                       ,intLimit, "ROLE_USER");
			List<UserInfoVo> objUserStatusCount = adminAccountMngService.getAdminOrUserCountByStatus("ROLE_USER");
			
			model.addAttribute("userList",    objUserList.getObjUserList());
			model.addAttribute("pagination",  objUserList);
			model.addAttribute("userId",      strUserId);
			model.addAttribute("startDate",   strStartDate);
			model.addAttribute("endDate",     strEndDate);
			
			model.addAttribute("stateCode",   intStateCode);
			model.addAttribute("statusCount", objUserStatusCount);
			model.addAttribute("adminId",     this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/userManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 회원 상세 정보
	@GetMapping("/user_detail")
	public String getUserDetailInfo(Model model, HttpSession session, @RequestParam(name= "no") int intUserNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			UserInfoVo objUserDetail = adminAccountMngService.getAdminOrUserDetailInfo(intUserNo, "ROLE_USER");
			
			model.addAttribute("userDetail", objUserDetail);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/userDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/userManage/userManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 회원 상세내역
	@GetMapping("/user_history")
	public String getUserHistory(Model model, HttpSession session, @RequestParam(name= "no") int intUserNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objUserHistoryList = adminAccountMngService.getAdminOrUserHistory(intUserNo);
			
			model.addAttribute("userNo",      intUserNo);
			model.addAttribute("userHistory", objUserHistoryList);
			model.addAttribute("adminId",     this.getLoginInfo(session).getUserName());
			
			return "admin/userManage/userHistory";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
