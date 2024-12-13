package com.team3.camping.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserAccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserAccountRestController {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
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
    
    // userId 중복체크
    @PostMapping("/user/user_duplicate_check")
    public ResponseEntity<ResultInfo> userDuplicateCheck(@RequestBody String strUserId) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objResultInfo = userAccountService.userDuplicateCheck(strUserId.replace("\"", ""));

        return ResponseEntity.ok(objResultInfo);
    }
    
    // 회원가입
    @PostMapping("/user/regist")
    public ResponseEntity<ResultInfo> userRegist(@RequestBody UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objUser.setUserPwd(passwordEncoder.encode(objUser.getUserPwd()));
        
        objResultInfo = userAccountService.userRegist(objUser);
        
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 회원정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    @PostMapping("/user/user_info_update")
    public ResponseEntity<ResultInfo> userInfoUpdate(HttpSession session, @RequestBody UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        if (objUser.getUserPwd() != null) {
            
            // 기존 비밀번호 체크
            String strRetVal = userAccountService.getPwd(this.getLoginInfo(session).getUserKey());
            
            // 입력한 기존 패스워드(문자열)와 DB의 패스워드(암호화된 코드) 비교
            if (!passwordEncoder.matches(objUser.getUserOriginPwd(), strRetVal)) {
                
                objResultInfo.setIntRetVal(1110);
                objResultInfo.setStrRetVal("기존 비밀번호가 일치하지 않습니다");
                
                return ResponseEntity.ok(objResultInfo);
                
            } else {
                // 암호화
                objUser.setUserPwd(passwordEncoder.encode(objUser.getUserPwd()));
            }
        }
        objUser.setUserId(this.getLoginInfo(session).getUserKey());
        
        // 회원 정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
        objResultInfo = userAccountService.userInfoUpdate(objUser);
        
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 회원 탈퇴
    @PostMapping("/user/secession")
    public ResponseEntity<ResultInfo> userSecession(HttpSession session, @RequestBody SecReasonVo objSecReason) {
        
        objSecReason.setUserId(this.getLoginInfo(session).getUserKey());
        objSecReason.setLoginState(this.getLoginInfo(session).getStateCode());
        
        return ResponseEntity.ok(userAccountService.userSecession(objSecReason));
    }
}