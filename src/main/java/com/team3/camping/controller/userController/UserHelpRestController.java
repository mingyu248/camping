package com.team3.camping.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserHelpService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserHelpRestController {
    
    @Autowired
    private UserHelpService userHelpService;
    
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
    
    // 문의 작성
    @PostMapping("/help_regist")
    public ResponseEntity<ResultInfo> helpRegist(HttpSession session, @RequestBody HelpVo objHelp) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            if (this.getLoginInfo(session).getStateCode() == 0) {
                objResultInfo.setIntRetVal(9700);
                objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
                
            } else {
                
                objHelp.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
                
                // 문의 등록
                objResultInfo = userHelpService.helpRegist(objHelp);
            }
        } catch (Exception ex) {
            log.info("==========[HelpRestController][helpRegist]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("컨트롤러 에러");
        }
        
        return ResponseEntity.ok(objResultInfo);
    }
    
    // 문의 삭제
    @PostMapping("/help_delete")
    public ResponseEntity<ResultInfo> helpDelete(HttpSession session, @RequestBody int intHelpNo) {
        
        ResultInfo  objResultInfo = new ResultInfo();
        String      strHelpUserId = userHelpService.getHelpUserId(intHelpNo);
        String      strStateCode  = userHelpService.getStateCode(intHelpNo);
        
        try {
            if (strHelpUserId != null || strStateCode != null) {
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("글을 찾을 수 없습니다");
            }
            
            if (this.getLoginInfo(session).getStateCode() == 0) {
                objResultInfo.setIntRetVal(8000);
                objResultInfo.setStrRetVal("로그인 이후 이용해주세요.");
                
            } else if (!strHelpUserId.equals(this.getLoginInfo(session).getUserKey())) {
                objResultInfo.setIntRetVal(8000);
                objResultInfo.setStrRetVal("나의 글만 수정할 수 있습니다");
                
            } else if (strStateCode.equals("답변전")) {
                objResultInfo.setIntRetVal(8000);
                objResultInfo.setStrRetVal("답변전 문의는 삭제할 수 없습니다");
                
            } else {
                // 문의 삭제
                objResultInfo = userHelpService.helpDelete(intHelpNo);
            }
        } catch (Exception ex) {
            log.info("==========[HelpRestController][helpDelete]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("컨트롤러 에러");
        }
        return ResponseEntity.ok(objResultInfo);
    }
}