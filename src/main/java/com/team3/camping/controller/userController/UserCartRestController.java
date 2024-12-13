package com.team3.camping.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserCartService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserCartRestController {
    
    @Autowired
    private UserCartService userCartService;
    
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
        
        log.info("=====[loginInfo]===== : " + objLoginInfo);
        
        return objLoginInfo;
    }
    
    // 캠핑장 찜
    @PostMapping("/cartin")
    public ResponseEntity<ResultInfo> cartin(HttpSession session, @RequestBody CartVo objCart) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objCart.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
        
        Integer intCartCheck = userCartService.cartCheck(objCart);
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else if (intCartCheck == null) {
            objResultInfo.setIntRetVal(7120);
            objResultInfo.setStrRetVal("캠핑장 찜하는 중 에러 발생");
            
        } else if (intCartCheck != 0) {
            objResultInfo.setIntRetVal(7120);
            objResultInfo.setStrRetVal("이미 찜한 캠핑장입니다");
            
        } else {
            objResultInfo = userCartService.addCart(objCart);
        }
        return ResponseEntity.ok(objResultInfo);
    }

    // 캠핑장 찜 삭제
    @PostMapping("/cart_delete")
    public ResponseEntity<ResultInfo> cartDelete(HttpSession session, @RequestBody CartVo objCart) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        objCart.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
        
        Integer intCartCheck = userCartService.cartCheck(objCart);
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
        } else if (intCartCheck == null) {
            objResultInfo.setIntRetVal(7140);
            objResultInfo.setStrRetVal("캠핑장 찜하는 중 에러 발생");
            
        } else if (intCartCheck == 0) {
            objResultInfo.setIntRetVal(7140);
            objResultInfo.setStrRetVal("찜되어 있지 않은 캠핑장입니다");
            
        } else {
            objResultInfo = userCartService.cartDelete(objCart);
        }
        return ResponseEntity.ok(objResultInfo);
    }
}
