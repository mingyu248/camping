package com.team3.camping.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.adminService.AdminCampMngService;
import com.team3.camping.service.adminService.AdminReviewMngService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminCampMngRestController {

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
	
	// 시/군/구 추출
	@GetMapping("/city_gun_gu_name")
	public ResponseEntity<List<CampInfoVo>> getCityGunGuName(@RequestParam(name = "province_name") String strProvinceName) {
		
		List<CampInfoVo> objCityGunGuNameList = adminCampMngService.groupByCityGunGuName(strProvinceName);
		
		return ResponseEntity.ok(objCityGunGuNameList);
	}
	
	// 캠핑장 비활성화
	@PostMapping("/camp_deactivate")
	public ResponseEntity<ResultInfo> campInfoStatusDeactivate(HttpSession session, @RequestParam(name = "no") int intCampNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminCampMngService.campInfoStatusDeactivate(intCampNo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 캠핑장 활성화
	@PostMapping("/camp_activate")
	public ResponseEntity<ResultInfo> campInfoStatusActivate(HttpSession session, @RequestParam(name = "no") int intCampNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminCampMngService.campInfoStatusActivate(intCampNo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 후기 정지
	@PostMapping("/stop_review")
	public ResponseEntity<ResultInfo> userCampReviewDeactivate(HttpSession session, @RequestBody ReviewVo objReview) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objReview.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminReviewMngService.userCampReviewDeactivate(objReview);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 후기 활성화
	@PostMapping("/activate_review")
	public ResponseEntity<ResultInfo> userCampReviewActivate(HttpSession session, @RequestBody ReviewVo objReview) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objReview.setAdminId(this.getLoginInfo(session).getUserName());
			
			objResultInfo = adminReviewMngService.userReviewActivate(objReview);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 후기 삭제
	@PostMapping("/delete_review")
	public ResponseEntity<ResultInfo> userCampReviewDelete(HttpSession session, @RequestBody ReviewVo objReview) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminReviewMngService.userReviewDelete(objReview);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 댓글 정지
	@PostMapping("/stop_comment")
	public ResponseEntity<ResultInfo> userCommentDeactivate(HttpSession session, @RequestParam(name = "no") int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminReviewMngService.userCommentDeactivate(intCommentNo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}

	// 댓글 활성화
	@PostMapping("/activate_comment")
	public ResponseEntity<ResultInfo> userCommentActivate(HttpSession session, @RequestParam(name = "no") int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminReviewMngService.userCommentActivate(intCommentNo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 댓글 삭제
	@PostMapping("/delete_comment")
	public ResponseEntity<ResultInfo> userCommentDelete(HttpSession session, @RequestParam(name = "no") int intCommentNo) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() != null) {
			objResultInfo = adminReviewMngService.userCommentDelete(intCommentNo);
			
		} else {
			objResultInfo.setIntRetVal(9900);
			objResultInfo.setStrRetVal("로그인 후 이용해 주세요.");
		}
		return ResponseEntity.ok(objResultInfo);
	}
	
	// 캠핑장 등록
	@PostMapping("regist_camp_info")
	public ResponseEntity<ResultInfo> registCampInfo(HttpSession session, @RequestParam("file") MultipartFile file) {
		
		log.info("==========================================[AdminCampMngRestController][registCampInfo]========================================== File : " + file);;
		
		ResultInfo objResultInfo = new ResultInfo();
		
		if (this.getLoginInfo(session).getUserName() == null) {
			
			objResultInfo.setIntRetVal(9998);
			objResultInfo.setStrRetVal("관리자 로그인 후 이용 가능");
		} else {
			objResultInfo = adminCampMngService.registCampInfo(file);
		}
		return ResponseEntity.ok(objResultInfo);
	}
}
