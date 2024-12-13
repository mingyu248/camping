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
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminCampMngService;
import com.team3.camping.service.adminService.AdminReviewMngService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminCampMngController {
	
	@Autowired
	private AdminCampMngService adminCampMngService;
	
	@Autowired
	private AdminReviewMngService adminReviewMngService;
	
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
	
	// 캠핑장 목록
	@GetMapping("/camp_manage")
	public String getCampInfoList(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")             int     intPage
                                                                   ,@RequestParam(defaultValue = "10", name = "limit")            int     intLimit
                                                                   ,@RequestParam(required = false,    name = "business_entity")  String  strBusinessEntity
                                                                   ,@RequestParam(required = false,    name = "facility_name")    String  strFacilityName
                                                                   ,@RequestParam(required = false,    name = "province_name")    String  strProvinceName
                                                                   
                                                                   ,@RequestParam(required = false,    name = "city_gun_gu_name") String  strCityGunGuName
                                                                   ,@RequestParam(required = false,    name = "start_date")       String  strStartDate
                                                                   ,@RequestParam(required = false,    name = "end_date")         String  strEndDate
                                                                   ,@RequestParam(required = false,    name = "status")           Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<CampInfoVo> objCountByStatus      = adminCampMngService.getCampCountByStatus();
			List<CampInfoVo> objProvinceNameList   = adminCampMngService.groupByProvinceName();
			List<CampInfoVo> objBusinessEntityList = adminCampMngService.groupByBusinessEntity();
			PagingUtil       objCampInfoList       = adminCampMngService.getCampInfoList(strBusinessEntity, strFacilityName, strProvinceName, strCityGunGuName, strStartDate
					                                                                    ,strEndDate, intStateCode, intPage, intLimit);
			
			model.addAttribute("statusCount",        objCountByStatus);
			model.addAttribute("campList",           objCampInfoList.getObjCampList());
			model.addAttribute("pagination",         objCampInfoList);
			model.addAttribute("businessEntity",     strBusinessEntity);
			model.addAttribute("facilityName",       strFacilityName);

			model.addAttribute("provinceName",       strProvinceName);
			model.addAttribute("cityGunGuName",      strCityGunGuName);
			model.addAttribute("startDate",          strStartDate);
			model.addAttribute("endDate",            strEndDate);
			model.addAttribute("stateCode",          intStateCode);

			model.addAttribute("provinceNameList",   objProvinceNameList);
			model.addAttribute("businessEntityList", objBusinessEntityList);
			model.addAttribute("adminId",            this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campManagement";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/adminMain");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 상세정보
	@GetMapping("/camp_detail")
	public String getCampDetailInfo(Model model, HttpSession session, @RequestParam(name = "no") int intCampNo) {
	
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			CampInfoVo       objCampInfo      = adminCampMngService.getCampInfoDetail(intCampNo);
			CampInfoVo       objCampImage     = adminCampMngService.getCampDetailImage(intCampNo);
			List<CampInfoVo> objCampImageList = adminCampMngService.getCampDetailImageList(intCampNo);
			
			model.addAttribute("campDetail",    objCampInfo);
			model.addAttribute("campImage",     objCampImage);
			model.addAttribute("campImageList", objCampImageList);
			model.addAttribute("adminId",       this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 데이터 추가
	@GetMapping("/add_camp_info")
	public String addCampInfo() {
		
		return "admin/campManage/addCampInfo";
	}
	
	// 캠핑장 위치
	@GetMapping("/camp_location")
	public String campLocation(Model model, HttpSession session, @RequestParam(name = "no") int intCampNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			CampInfoVo objCampInfo = adminCampMngService.getCampInfoDetail(intCampNo);
			
			model.addAttribute("campDetail", objCampInfo);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campLocation";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 후기 목록
	@GetMapping("/camp_review")
	public String getCampReviewList(Model model, HttpSession session, @RequestParam(name = "no")                              int     intCampNo
			                                                         ,@RequestParam(defaultValue = "1",  name = "page")       int     intPage
                                                                     ,@RequestParam(defaultValue = "10", name = "limit")      int     intLimit
                                                                     ,@RequestParam(required = false,    name = "title")      String  strReviewTitle
                                                                     ,@RequestParam(required = false,    name = "id")         String  strUserAccount
                                                              
			                                                         ,@RequestParam(required = false,    name = "start_date") String  strStartDate
			                                                         ,@RequestParam(required = false,    name = "end_date")   String  strEndDate
			                                                         ,@RequestParam(required = false,    name = "status")     Integer intStateCode) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			CampInfoVo objCampInfo       = adminCampMngService.getCampInfoDetail(intCampNo);
			PagingUtil objCampReviewList = adminReviewMngService.getCampReivewList(intCampNo, strReviewTitle, strUserAccount, strStartDate, strEndDate
					                                                              ,intStateCode, intPage, intLimit);
			
			model.addAttribute("reviewList",  objCampReviewList.getObjReviewList());
			model.addAttribute("campNo",      intCampNo);
			model.addAttribute("pagination",  objCampReviewList);
			model.addAttribute("userAccount", strUserAccount);
			model.addAttribute("reviewTitle", strReviewTitle);
			
			model.addAttribute("startDate",   strStartDate);
			model.addAttribute("endDate",     strEndDate);
			model.addAttribute("stateCode",   intStateCode);
			model.addAttribute("campDetail",  objCampInfo);
			model.addAttribute("adminId",     this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campReview";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}

	// 캠핑장 후기 상세정보
	@GetMapping("/review_detail")
	public String getCampReivewDetail(Model model, HttpSession session, @RequestParam(name = "no") int intRreviewNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			ReviewVo              objReviewDetail      = adminReviewMngService.getCampReviewDetail(intRreviewNo);
			List<ReviewCommentVo> objCommentList       = adminReviewMngService.getCampReviewCommentList(intRreviewNo);
			int                   intCommentTotalCount = adminReviewMngService.getCampReviewCommentCount(intRreviewNo);
			
			objReviewDetail.setReviewDetail(objReviewDetail.getReviewDetail().replace("\n", "<br>"));
			
			model.addAttribute("reviewNo",     intRreviewNo);
			model.addAttribute("reviewDetail", objReviewDetail);
			model.addAttribute("commentList",  objCommentList);
			model.addAttribute("commentCount", intCommentTotalCount);
			model.addAttribute("adminId",      this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campReviewDetail";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 후기 상세내역
	@GetMapping("/review_history")
	public String getCampReivewHistory(Model model, HttpSession session, @RequestParam(name = "no") int intRreviewNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<ReviewVo> objReviewHisList = adminReviewMngService.getCampReviewHistory(intRreviewNo);
			
			model.addAttribute("reviewNo",      intRreviewNo);
			model.addAttribute("reviewHisList", objReviewHisList);
			model.addAttribute("adminId",       this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campReviewHistory";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
	
	// 캠핑장 예약내역
	@GetMapping("/camp_reservation")
	public String campReservationHistory(Model model, HttpSession session, @RequestParam(name = "no")                         int intCampNo
																		  ,@RequestParam(defaultValue = "1",  name = "page")  int     intPage
            															  ,@RequestParam(defaultValue = "10", name = "limit") int     intLimit) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			CampInfoVo objCampInfo        = adminCampMngService.getCampInfoDetail(intCampNo);
			PagingUtil objReservationList = adminCampMngService.getReservationListByCamp(intCampNo, intPage, intLimit);
			
			model.addAttribute("reservationList", objReservationList.getObjReservationList());
			model.addAttribute("pagination",      objReservationList);
			model.addAttribute("campNo",          intCampNo);
			
			model.addAttribute("campDetail", objCampInfo);
			model.addAttribute("adminId",    this.getLoginInfo(session).getUserName());
			
			return "admin/campManage/campReservation";
			
		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("데이터를 불러올 수 없음");
			objResultInfo.setStrRedirectUrl("admin/campManage/campManagement");
			
			model.addAttribute("resultInfo", objResultInfo);
			
			return "admin/error";
		}
	}
}
