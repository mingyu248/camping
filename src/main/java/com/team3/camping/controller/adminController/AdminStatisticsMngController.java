package com.team3.camping.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminCampMngService;
import com.team3.camping.service.adminService.AdminStatisticsMngService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsMngController {

	@Autowired
	private AdminStatisticsMngService adminStatisticsMngService;
	
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
	
	// 회원 현황(자체 서비스)
	@GetMapping("/user_statistics")
	public String getUserStatistics(Model model, HttpSession session) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objUserStatisticsByGender   = adminStatisticsMngService.getUserStatisticsByGender();
			List<UserInfoVo> objUserStatisticsByAgeGroup = adminStatisticsMngService.getUserStatisticsByAgeGroup();
			List<UserInfoVo> objUserStatisticsByStatus   = adminStatisticsMngService.getUserStatisticsByStatus();
			
			model.addAttribute("genderStatistics",   objUserStatisticsByGender);
			model.addAttribute("ageGroupStatistics", objUserStatisticsByAgeGroup);
			model.addAttribute("statusStatistics",   objUserStatisticsByStatus);
			
			model.addAttribute("adminId", this.getLoginInfo(session).getUserName());
			
			return "admin/statisticsManage/userStatisticsManage";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 회원 현황(소셜 로그인)
	@GetMapping("/social_statistics")
	public String getSocialStatistics(Model model, HttpSession session) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<UserInfoVo> objSocialStatisticsByPlatform = adminStatisticsMngService.getSocialStatisticsByPlatform();
			List<UserInfoVo> objSocialStatisticsByGender   = adminStatisticsMngService.getSocialStatisticsByGender();
			List<UserInfoVo> objSocialStatisticsByAgeGroup = adminStatisticsMngService.getSocialStatisticsByAgeGroup();
			List<UserInfoVo> objSocialStatisticsByStatus   = adminStatisticsMngService.getSocialStatisticsByStatus();
			
			model.addAttribute("platformStatistics", objSocialStatisticsByPlatform);
			model.addAttribute("genderStatistics",   objSocialStatisticsByGender);
			model.addAttribute("ageGroupStatistics", objSocialStatisticsByAgeGroup);
			model.addAttribute("statusStatistics",   objSocialStatisticsByStatus);
			
			model.addAttribute("adminId", this.getLoginInfo(session).getUserName());
			
			return "admin/statisticsManage/socialStatisticsManage";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 현황
	@GetMapping("/camp_statistics")
	public String getCampStatistics(Model model, HttpSession session) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<CampInfoVo> objCampProvinceNameList      = adminCampMngService.groupByProvinceName();
			List<CampInfoVo> objCampStatisticsByProvince  = adminStatisticsMngService.getCampStatisticsByProvince();
			List<CampInfoVo> objCampStatisticsByCityGunGu = adminStatisticsMngService.getCampStatisticsByCityGunGu("강원도");
			
			model.addAttribute("provinceName",        objCampProvinceNameList);
			model.addAttribute("provinceStatistics",  objCampStatisticsByProvince);
			model.addAttribute("cityGunGuStatistics", objCampStatisticsByCityGunGu);
			model.addAttribute("adminId",             this.getLoginInfo(session).getUserName());
			
			return "admin/statisticsManage/campStatisticsManage";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 문의 현황
	@GetMapping("/help_statistics")
	public String getHelpStatistics(Model model, HttpSession session) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<HelpVo> objHelpStatisticsByStatus   = adminStatisticsMngService.getHelpStatisticsByStatus();
			List<HelpVo> objHelpStatisticsByRegWeek  = adminStatisticsMngService.getHelpStatisticsByRegWeek();
			List<HelpVo> objHelpStatisticsByRegMonth = adminStatisticsMngService.getHelpStatisticsByRegMonth();
			
			model.addAttribute("statusStatistics",   objHelpStatisticsByStatus);
			model.addAttribute("regWeekStatistics",  objHelpStatisticsByRegWeek);
			model.addAttribute("regMonthStatistics", objHelpStatisticsByRegMonth);
			model.addAttribute("adminId",            this.getLoginInfo(session).getUserName());
			
			return "admin/statisticsManage/helpStatisticsManage";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 지역별 예약 현황
	@GetMapping("/reservation_statistics")
	public String getReservationStatistics(Model model, HttpSession session) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<CampInfoVo>    objCampProvinceNameList             = adminCampMngService.groupByProvinceName();
			List<ReservationVo> objReservationStatisticsByProvince  = adminStatisticsMngService.getReservationStatisticsByProvince();
			List<ReservationVo> objReseravtionStatisticsByCityGunGu = adminStatisticsMngService.getReservationStatisticsByCityGunGu("강원도");
			List<ReservationVo> objReservationStatisticsByRegWeek   = adminStatisticsMngService.getReservationStatisticsByRegWeek();
			List<ReservationVo> objReservationStatisticsByRegMonth  = adminStatisticsMngService.getReservationStatisticsByMonth();
			
			model.addAttribute("provinceName",        objCampProvinceNameList);
			model.addAttribute("provinceStatistics",  objReservationStatisticsByProvince);
			model.addAttribute("cityGunGuStatistics", objReseravtionStatisticsByCityGunGu);
			model.addAttribute("regWeekStatistics",   objReservationStatisticsByRegWeek);
			model.addAttribute("regMonthStatistics",  objReservationStatisticsByRegMonth);
			
			model.addAttribute("adminId",             this.getLoginInfo(session).getUserName());
			
			return "admin/statisticsManage/reservationStatisticsManage";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
