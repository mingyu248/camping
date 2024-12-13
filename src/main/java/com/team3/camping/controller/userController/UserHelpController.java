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

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserHelpService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserHelpController {
    
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
        
        log.info("=====[loginInfo]===== : " + objLoginInfo);
        
        return objLoginInfo;
    }
    
    // 문의 상세
    @GetMapping("/help_detail")
    public String helpDetail(Model model, HttpSession session, @RequestParam("help_no") int intHelpNo) {
        
        ResultInfo  objResultInfo = new ResultInfo();
        LoginInfoVo objLoginInfo  = this.getLoginInfo(session);
        String      strHelpUserId = userHelpService.getHelpUserId(intHelpNo);
        HelpVo      objHelpVo     = new HelpVo();
        
        if (objLoginInfo.getStateCode() == 0) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "/user/error";
            
        } else if (strHelpUserId == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("문의 작성자 체크 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "/user/error";
            
        } else if (!strHelpUserId.equals(objLoginInfo.getUserKey())) {
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("나의 문의만 확인할 수 있습니다");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "/user/error";
            
        } else {
            objHelpVo = userHelpService.getHelp(intHelpNo);
            
            if (objHelpVo == null) {
                
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("문의 정보 가져오는 중 에러");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "/user/error";
                
            } else {
                model.addAttribute("help",       objHelpVo);
                model.addAttribute("userId",     objLoginInfo.getUserName());
                
                return "user/help/helpDetail";
            }
        }

    }
    
    // 문의 작성
    @GetMapping("/help_regist")
    public String helpRegist(Model model, HttpSession session) {
        
        ResultInfo  objResultInfo = new ResultInfo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            
            objResultInfo.setIntRetVal(8000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("userId", this.getLoginInfo(session).getUserName());
            
            return "user/help/helpRegist";
        }
    }
    
    // 문의 리스트
    @GetMapping("/my_help")
    public String helpList(Model model, HttpSession session, @RequestParam(defaultValue = "1",   name = "page")       int     intPage
                                                            ,@RequestParam(defaultValue = "10",  name = "limit")      int     intLimit
                                                            ,@RequestParam(required     = false, name = "help_title") String  strHelpTitle
                                                            ,@RequestParam(required     = false, name = "state_code") Integer intStateCode) {
        
        ResultInfo          objResultInfo   = new ResultInfo();
        LoginInfoVo         objLoginInfo    = this.getLoginInfo(session);
        Map<String, Object> searchOptionMap = new HashMap<>();
        List<HelpVo>        objHelpList     = new ArrayList<>();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            
            searchOptionMap.put("currentPage", intPage);
            searchOptionMap.put("limit", intLimit);
            searchOptionMap.put("helpTitle", strHelpTitle);
            searchOptionMap.put("stateCode", intStateCode);
            searchOptionMap.put("userKey", objLoginInfo.getUserKey());
            
            PagingUtil objPagingUtil = userHelpService.getHelpList(searchOptionMap);
            
            if (objPagingUtil == null) {
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("문의 리스트 가져오는 중 에러");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "user/error";
                
            } else {
                objHelpList = objPagingUtil.getObjHelpList();
                
                model.addAttribute("searchOption", searchOptionMap);
                model.addAttribute("helpList",     objHelpList);
                model.addAttribute("userId",       objLoginInfo.getUserName());
                
                return "user/myPage/myHelp";
            }
        }
    }
}
