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
public class AdminAccountMngController {

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
	
	// 관리자 목록
	@GetMapping("/admin_manage")
	public String getAdminList(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")       int     intPage
	                                                            ,@RequestParam(defaultValue = "10", name = "limit")      int     intLimit
	                                                            ,@RequestParam(required = false,    name = "id")         String  strAdminId
	                                                            ,@RequestParam(required = false,    name = "start_date") String  strStartDate
	                                                            ,@RequestParam(required = false,    name = "end_date")   String  strEndDate
	                                                            
			                                                    ,@RequestParam(required = false,    name = "status")     Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			PagingUtil       objAdminList        = adminAccountMngService.getAdminOrUserList(strAdminId, strStartDate, strEndDate, intStateCode, intPage
					                                                                        ,intLimit, "ROLE_ADMIN");
			List<UserInfoVo> objAdminStatusCount = adminAccountMngService.getAdminOrUserCountByStatus("ROLE_ADMIN");
			
			model.addAttribute("adminList",   objAdminList.getObjUserList());
			model.addAttribute("pagination",  objAdminList);
			model.addAttribute("adminId",     strAdminId);
			model.addAttribute("startDate",   strStartDate);
			model.addAttribute("endDate",     strEndDate);
			
			model.addAttribute("stateCode",   intStateCode);
			model.addAttribute("statusCount", objAdminStatusCount);
			model.addAttribute("userId",      this.getLoginInfo(session).getUserName());
		
			return "admin/adminManage/adminManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 관리자 등록
	@GetMapping("/admin_regist")
	public String adminRegist() {
		
		return "admin/adminManage/adminRegist";
	}
	
	// 관리자 상세 정보
	@GetMapping("/admin_detail")
	public String getAdminDetailInfo(Model model, HttpSession session, @RequestParam(name= "no") int intAdminNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			UserInfoVo objAdminDetail = adminAccountMngService.getAdminOrUserDetailInfo(intAdminNo, "ROLE_ADMIN");
			
			model.addAttribute("adminDetail", objAdminDetail);
			model.addAttribute("userId",      this.getLoginInfo(session).getUserName());
			
			return "admin/adminManage/adminDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminManage/adminManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 관리자 상세내역
	@GetMapping("/admin_history")
	public String getAdminHistory(Model model, HttpSession session, @RequestParam(name= "no") int intAdminNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objAdminHistoryList = adminAccountMngService.getAdminOrUserHistory(intAdminNo);
			
			model.addAttribute("adminHistory", objAdminHistoryList);
			model.addAttribute("adminNo",      intAdminNo);
			model.addAttribute("userId",       this.getLoginInfo(session).getUserName());
			
			return "admin/adminManage/adminHistory";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminManage/adminManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 관리자 정보수정
	@GetMapping("/admin_update")
	public String updateAdminInfo(Model model, HttpSession session, @RequestParam(name= "no") int intAdminNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			UserInfoVo objAdminDetail = adminAccountMngService.getAdminOrUserDetailInfo(intAdminNo, "ROLE_ADMIN");
			
			model.addAttribute("userId",      this.getLoginInfo(session).getUserName());
			model.addAttribute("adminDetail", objAdminDetail);
			
			return "admin/adminManage/adminUpdate";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminManage/adminManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
