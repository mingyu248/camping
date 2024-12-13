package com.team3.camping.controller.userController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserAccountController {
    
    @Autowired
    private UserAccountService userAccountService;
    
    // 현재 로그인 정보 반환(FK로 사용할 userKey, 이름 표시를 위한 userName, 로그인 상태 판별을 위한 stateCode)
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
    
    // 회원가입
	@GetMapping("join")
	public String userInfoRegist(Model model, HttpSession session) {
		
        model.addAttribute("userId", this.getLoginInfo(session).getUserName());
		
		return "user/userRegist";
	}
	
	// 내 정보
	@GetMapping("/user/my_info")
    public String myInfo(Model model, HttpSession session) {
        
	    ResultInfo objResultInfo = new ResultInfo();
	    UserInfoVo objUserInfo   = new UserInfoVo();
	    
	    if (this.getLoginInfo(session).getStateCode() == 0) { // 로그아웃 상태
	        objResultInfo.setIntRetVal(8000);
	        objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
	        
	        model.addAttribute("resultInfo", objResultInfo);
	        
	        return "user/error";
	        
	    } else if (this.getLoginInfo(session).getStateCode() == 1) { // 자체 로그인
	        
	        // 내 정보 가져오기(자체로그인)
	        objUserInfo = userAccountService.getUserInfo(this.getLoginInfo(session).getUserKey());
            
            if (objUserInfo == null) {
                
                objResultInfo.setIntRetVal(1110);
                objResultInfo.setStrRetVal("회원 정보 가져오는 중 에러 발생");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "user/error";
            }
	    } else if (this.getLoginInfo(session).getStateCode() == 2){ // 소셜 로그인
	        
	        // 내 정보 가져오기(자체로그인)
            objUserInfo = userAccountService.getSocialUserInfo(this.getLoginInfo(session).getUserKey());
            
            if (objUserInfo == null) {
                
                objResultInfo.setIntRetVal(1110);
                objResultInfo.setStrRetVal("회원 정보 가져오는 중 에러 발생");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "user/error";
            }
	    }
        model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
        model.addAttribute("loginState", this.getLoginInfo(session).getStateCode());
        model.addAttribute("userInfo",   objUserInfo);
        
        return "user/myPage/myInfo";
    }
    
    // 이메일 수정
    @GetMapping("/user/email_update")
    public String emailUpdate(Model model, HttpSession session, @RequestParam(defaultValue = "", name="user_email") String strUserEmail) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userEmail",  strUserEmail);
            model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
            
            return "user/myPage/userInfoUpdate/emailUpdate";
        }
    }
    
    // 비밀번호 수정
    @GetMapping("/user/password_update")
    public String passwordUpdate(Model model, HttpSession session) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userId", this.getLoginInfo(session).getUserName());
            
            return "user/myPage/userInfoUpdate/passwordUpdate";
        }
    }
    
    // 주소 수정
    @GetMapping("/user/address_update")
    public String addressUpdate(Model model, HttpSession session, @RequestParam("zip_code")  String strZipCode
                                                                 ,@RequestParam("base_addr") String strBaseAddr
                                                                 ,@RequestParam("dtl_addr")  String strDtlAddr) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("zipCode",  strZipCode);
            model.addAttribute("baseAddr", strBaseAddr);
            model.addAttribute("dtlAddr",  strDtlAddr);
            model.addAttribute("userId",   this.getLoginInfo(session).getUserName());
            model.addAttribute("loginState", this.getLoginInfo(session).getStateCode());
            
            return "user/myPage/userInfoUpdate/addressUpdate";
        }
    }
    
    // 생년월일 수정
    @GetMapping("/user/birth_update")
    public String birthUpdate(Model model, HttpSession session, @RequestParam("user_birth") String strUserBirth) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userBirth",  strUserBirth);
            model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
            model.addAttribute("loginState", this.getLoginInfo(session).getStateCode());
            
            return "user/myPage/userInfoUpdate/birthUpdate";
        }
    }
    
    // 성별 수정
    @GetMapping("/user/gender_update")
    public String genderUpdate(Model model, HttpSession session, @RequestParam("gender") String strGender) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("gender", strGender);
            model.addAttribute("userId", this.getLoginInfo(session).getUserName());
            model.addAttribute("loginState", this.getLoginInfo(session).getStateCode());
            
            return "user/myPage/userInfoUpdate/genderUpdate";
        }
    }
    
    // 이름 수정
    @GetMapping("/user/name_update")
    public String nameUpdate(Model model, HttpSession session, @RequestParam("user_name") String strUserName) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userName", strUserName);
            model.addAttribute("userId",   this.getLoginInfo(session).getUserName());
            
            return "user/myPage/userInfoUpdate/nameUpdate";
        }
    }
    
    // 전화번호 수정
    @GetMapping("/user/tel_update")
    public String telUpdate(Model model, HttpSession session, @RequestParam("user_tel_num") String strUserTelNum) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userTelNum", strUserTelNum);
            model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
            model.addAttribute("loginState", this.getLoginInfo(session).getStateCode());
            
            return "user/myPage/userInfoUpdate/telUpdate";
        }
    }
    
    // 회원탈퇴 팝업
    @GetMapping("/user/secession")
    public String userSecession(Model model, HttpSession session) {
        
        ResultInfo objResultInfo = new ResultInfo();

        List<SecReasonVo> objSecessionReasonList = new ArrayList<>();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            objSecessionReasonList = userAccountService.getSecessionReasonList();
            
            if (objSecessionReasonList == null) {
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("탈퇴 사유 리스트 가져오는 중 에러");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "user/error";
            }
            
            model.addAttribute("secessionReasonList", objSecessionReasonList);
            
            return "user/myPage/userSecession";
        }
    }
}