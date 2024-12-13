package com.team3.camping.controller.userController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserCartService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserCartController {
	
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
        return objLoginInfo;
    }
    
    // 찜 리스트
    @GetMapping("/my_cart")
    public String myCart(Model model, HttpSession session, @RequestParam(defaultValue = "1",  name = "page")  int intPage
                                                          ,@RequestParam(defaultValue = "10", name = "limit") int intLimit) {
        
        Map<String, Object> cartPagingMap = new HashMap<>();
        
        ResultInfo   objResultInfo = new ResultInfo();
        PagingUtil   objPagingUtil = new PagingUtil();
        List<CartVo> objCartList   = new ArrayList<>();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "/user/error";
            
        } else {
            cartPagingMap.put("currentPage", intPage);
            cartPagingMap.put("limit",       intLimit);
            cartPagingMap.put("userKey",     this.getLoginInfo(session).getUserKey());
            
            objPagingUtil = userCartService.getCartList(cartPagingMap);
            
            objCartList = objPagingUtil.getObjCartList();
            
            model.addAttribute("searchOption", cartPagingMap);
            model.addAttribute("userId",       this.getLoginInfo(session).getUserName());
            model.addAttribute("cartList",     objCartList);
            
            return "user/myPage/MyCart";
        }
    }
}