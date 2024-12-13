package com.team3.camping.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminCampMngService;
import com.team3.camping.service.adminService.AdminReservationMngService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminReservationMngController {

	@Autowired
	private AdminReservationMngService adminReservationMngService;
	
	@Autowired
	private AdminCampMngService adminCampMngService;
	
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
	
	// 캠핑장 예약 목록
	@GetMapping("/reservation_manage")
	public String getUserCampReservationList(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")         int     intPage
                                                                              ,@RequestParam(defaultValue = "10", name = "limit")        int     intLimit
                                                                              ,@RequestParam(required = false,    name = "user_account") String  strUserAccount
                                                                              ,@RequestParam(required = false,    name = "province")     String  strProvinceName
                                                                              ,@RequestParam(required = false,    name = "city_gun_gu")  String  strCityGunGuName
                                                                              
                                                                              ,@RequestParam(required = false,    name = "facility")     String  strFacilityName
                                                                              ,@RequestParam(required = false,    name = "start_date")   String  strStartDate
                                                                              ,@RequestParam(required = false,    name = "end_date")     String  strEndDate
                                                                              ,@RequestParam(required = false,    name = "status")       Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<CampInfoVo>    objProvinceNameList         = adminCampMngService.groupByProvinceName();
			List<ReservationVo> objReservationCountByStatus = adminReservationMngService.getCampReservationCountByStatus();
			PagingUtil          objReservationList          = adminReservationMngService.getUserCampReservationList(strUserAccount, strProvinceName, strCityGunGuName, strFacilityName, strStartDate
					                                                                                               ,strEndDate, intStateCode, intPage, intLimit);
			model.addAttribute("provinceNameList", objProvinceNameList);
			model.addAttribute("statusCount",      objReservationCountByStatus);
			model.addAttribute("reservationList",  objReservationList.getObjReservationList());
			model.addAttribute("pagination",       objReservationList);
			model.addAttribute("userAccount",      strUserAccount);
			
			model.addAttribute("provinceName",     strProvinceName);
			model.addAttribute("cityGunGuName",    strCityGunGuName);
			model.addAttribute("facilityName",     strFacilityName);
			model.addAttribute("startDate",        strStartDate);
			model.addAttribute("endDate",          strEndDate);
			
			model.addAttribute("stateCode",        intStateCode);
			model.addAttribute("adminId",          this.getLoginInfo(session).getUserName());

			return "admin/reservationManage/reservationManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/error";
		}
	}
	
	// 예약 상세정보
	@GetMapping("/reservation_detail")
	public String getReservationDetailInfo(Model model, HttpSession session, @RequestParam(name = "no") int intReservationNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			ReservationVo objReservationDetail = adminReservationMngService.getReservationDetailInfo(intReservationNo);
			
			model.addAttribute("reservationDetail", objReservationDetail);
			model.addAttribute("adminId",           this.getLoginInfo(session).getUserName());
			
			return "admin/reservationManage/reservationDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/error";
		}
	}
	
	// 예약 상세내역
	@GetMapping("/reservation_history")
	public String getReservationHistoryList(Model model, HttpSession session, @RequestParam(name = "no") int intReservationNo) {
		
		log.info("====================================[AdminReservationMngController][getReservationHistoryList]==================================== intReservationNo : " + intReservationNo);
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<ReservationVo> objReservationHistoryList = adminReservationMngService.getReservationHistoryList(intReservationNo);
			
			model.addAttribute("reservationNo",      intReservationNo);
			model.addAttribute("reservationHistory", objReservationHistoryList);
			model.addAttribute("adminId",            this.getLoginInfo(session).getUserName());
			
			return "admin/reservationManage/reservationHistory";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/error";
		}
	}
}
