package com.team3.camping.controller.userController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.LoginInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewCommentVo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.domain.SuggestionVo;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.service.userService.UserCampService;
import com.team3.camping.service.userService.UserCartService;
import com.team3.camping.service.userService.UserReservationService;
import com.team3.camping.service.userService.UserReviewService;
import com.team3.camping.service.userService.UserSuggestionService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserCampController {
    
    @Autowired
    private UserReviewService userReviewService;
    
    @Autowired
    private UserCampService userCampService;
    
    @Autowired
    private UserCartService userCartService;
    
    @Autowired
    private UserSuggestionService userSuggestionService;
    
    @Autowired
    private UserReservationService userReservationService;
    
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
    
    // 캠핑장 리스트
    @GetMapping("/camp_list")
    public String campList(Model model, HttpSession session, @RequestParam(defaultValue = "1",     name = "page")                          int     intPage
                                                            ,@RequestParam(defaultValue = "10",    name = "limit")                         int     intLimit
                                                            ,@RequestParam(required     = false,   name = "search_key")                    String  strSearchKey
                                                            ,@RequestParam(required     = false,   name = "province_name")                 String  strProvinceName
                                                            ,@RequestParam(required     = false,   name = "business_entity")               String  strBusinessEntity
                                                            
                                                            ,@RequestParam(required     = false,   name = "camping_type")                  String  strCampingType
                                                            ,@RequestParam(defaultValue = "false", name = "electricity_subfacility")       Boolean blnElectricitySubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "hotWater_subfacility")          Boolean blnHotWaterSubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "wireless_internet_subfacility") Boolean blnWirelessInternetSubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "firewood_sales_subfacility")    Boolean blnFirewoodSalesSubfacility
                                                            
                                                            ,@RequestParam(defaultValue = "false", name = "walking_trail_subfacility")     Boolean blnWalkingTrailSubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "water_play_area_subfacility")   Boolean blnWaterPlayAreaSubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "playground_subfacility")        Boolean blnPlaygroundSubfacility
                                                            ,@RequestParam(defaultValue = "false", name = "mart_subfacility")              Boolean blnMartSubfacility) {
        
        ResultInfo          objResultInfo   = new ResultInfo();
        Map<String, Object> searchOptionMap = new HashMap<>();
        
        searchOptionMap.put("currentPage",                 intPage);
        searchOptionMap.put("limit",                       intLimit);
        searchOptionMap.put("searchKey",                   strSearchKey);
        searchOptionMap.put("provinceName",                strProvinceName);
        searchOptionMap.put("businessEntity",              strBusinessEntity);
        
        searchOptionMap.put("campingType",                 strCampingType);
        searchOptionMap.put("electricitySubfacility",      blnElectricitySubfacility);
        searchOptionMap.put("hotWaterSubfacility",         blnHotWaterSubfacility);
        searchOptionMap.put("wirelessInternetSubfacility", blnWirelessInternetSubfacility);
        searchOptionMap.put("firewoodSalesSubfacility",    blnFirewoodSalesSubfacility);
        
        searchOptionMap.put("walkingTrailSubfacility",     blnWalkingTrailSubfacility);
        searchOptionMap.put("waterPlayAreaSubfacility",    blnWaterPlayAreaSubfacility);
        searchOptionMap.put("playgroundSubfacility",       blnPlaygroundSubfacility);
        searchOptionMap.put("martSubfacility",             blnMartSubfacility);
        
        PagingUtil       objPagingUtil   = userCampService.getCampList(searchOptionMap);
        List<CampInfoVo> objCampInfoList = new ArrayList<>();
        
        if (objPagingUtil == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
        } else {
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("SUCCESS");
            objCampInfoList = objPagingUtil.getObjCampList();
        }
        model.addAttribute("searchOption", searchOptionMap);
        model.addAttribute("resultInfo",   objResultInfo);
        model.addAttribute("campList",     objCampInfoList);
        model.addAttribute("userId",       this.getLoginInfo(session).getUserName());
        
        return "user/camp/campList";
    }
    
    // 캠핑장 상세(소개)
    @GetMapping("/camp_detail")
    public String campDetail(Model model, HttpSession session, @RequestParam("camp_no") int intCampNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        // 캠핑장 상세(소개)
        CampInfoVo objCampDetail = userCampService.getCampDetail(intCampNo);
        
        if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 상세정보 가져오는 중 에러");
        } else {
            // 조회수 증가
            objResultInfo = userCampService.campViewIncrease(intCampNo);
        }
        
        if (this.getLoginInfo(session).getStateCode() != 0) {
            // 추천 상태 확인
            SuggestionVo objSuggestionVo = new SuggestionVo();
            
            objSuggestionVo.setCampNo(intCampNo);
            objSuggestionVo.setUserKey(this.getLoginInfo(session).getUserKey());
            objCampDetail.setSuggestionCheck(userSuggestionService.suggestionCheck(objSuggestionVo) == 1);
            
            // 찜 상태 확인
            CartVo objCart = new CartVo();
            
            objCart.setCampNo(intCampNo);
            objCart.setUserIdOrEmail(this.getLoginInfo(session).getUserKey());
            objCampDetail.setCartCheck(userCartService.cartCheck(objCart) == 1);
        }
        
        model.addAttribute("resultInfo", objResultInfo);
        model.addAttribute("campNo",     intCampNo);
        model.addAttribute("camp",       objCampDetail);
        model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
        
        return "user/camp/campDetail";
    }
    
    // 캠핑장 상세(위치)
    @GetMapping("/camp_detail_location")
    public String campDetailLocation(Model model, HttpSession session, @RequestParam("camp_no") int intCampNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        // 캠핑장 상세(위치)
        CampInfoVo objCampDetail = userCampService.getCampDetail(intCampNo);
        
        if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
        } else {
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("SUCCESS");
        }

        model.addAttribute("resultInfo", objResultInfo);
        model.addAttribute("camp",       objCampDetail);
        model.addAttribute("campNo",     intCampNo);
        model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
        
        return "user/camp/campDetailLocation";
    }
    
    // 캠핑장 상세(후기 목록)
    @GetMapping("/camp_detail_review")
    public String campDetailReview(Model model, HttpSession session, @RequestParam("camp_no") int intCampNo
                                                                    ,@RequestParam(defaultValue = "1", name = "page")   int intPage
                                                                    ,@RequestParam(defaultValue = "10", name = "limit") int intLimit) {
        
        ResultInfo          objResultInfo   = new ResultInfo();
        Map<String, Object> reviewPagingMap = new HashMap<>();
        
        reviewPagingMap.put("campNo",      intCampNo);
        reviewPagingMap.put("currentPage", intPage);
        reviewPagingMap.put("limit",       intLimit);
        
        List<ReviewVo> getReviewList = userReviewService.getReviewList(reviewPagingMap);
        
        CampInfoVo objCampDetail = userCampService.getCampDetail(intCampNo);
        
        if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
        } else {
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("SUCCESS");
        }
        
        model.addAttribute("searchOption", reviewPagingMap);
        model.addAttribute("campNo",       intCampNo);
        model.addAttribute("camp",         objCampDetail);
        model.addAttribute("reviewList",   getReviewList);
        model.addAttribute("userId",       this.getLoginInfo(session).getUserName());
        model.addAttribute("pageInfo",     "campPage");
        
        return "user/camp/campDetailReview";
    }
    
    // 후기 상세
    @GetMapping("/review_detail")
    public String reviewDetail(Model model, HttpSession session, @RequestParam("review_no") int    intReviewNo
                                                                ,@RequestParam("camp_no")   int    intCampNo
                                                                ,@RequestParam("page_info") String strPageInfo) {
        
        ResultInfo      objResultInfo = new ResultInfo();
        ReviewCommentVo objComment    = new ReviewCommentVo();
        
        model.addAttribute("userId",  this.getLoginInfo(session).getUserName());
        model.addAttribute("userKey", this.getLoginInfo(session).getUserKey());
        
        ReviewVo objReviewVo = userReviewService.reviewDetail(intReviewNo);
        
        objComment.setReviewNo(intReviewNo);
        
        List<ReviewCommentVo> commentList   = userReviewService.getComment(objComment); 
        CampInfoVo            objCampDetail = userCampService.getCampDetail(intCampNo); // 캠핑장 상세
        
        
        
        if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("reviewDetail", objReviewVo);
            model.addAttribute("campNo",       intCampNo);
            model.addAttribute("camp",         objCampDetail);
            model.addAttribute("commentList",  commentList);
            model.addAttribute("pageInfo",     strPageInfo);
            
            return "user/review/reviewDetail";
        }
    }
    
    // 후기 수정 페이지로 이동
    @GetMapping("/review_update")
    public String reviewUpdate(Model model, HttpSession session, @RequestParam("review_no") int    intReviewNo
                                                                ,@RequestParam("page_info") String strPageInfo) {
        
        ResultInfo objResultInfo   = new ResultInfo();
        ReviewVo   objReviewVo     = userReviewService.reviewDetail(intReviewNo);
        String     strReviewWriter = userReviewService.getReviewWriter(intReviewNo);
        CampInfoVo objCampDetail   = userCampService.getCampDetail(objReviewVo.getCampNo()); // 캠핑장 상세
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else if (strReviewWriter == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 작성자 체크 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else if (!strReviewWriter.equals(this.getLoginInfo(session).getUserKey())) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("나의 후기만 수정할 수 있습니다");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("camp",         objCampDetail);
            model.addAttribute("campNo",       objReviewVo.getCampNo());
            model.addAttribute("reviewDetail", objReviewVo);
            model.addAttribute("reviewNo",     intReviewNo);
            model.addAttribute("userId",       this.getLoginInfo(session).getUserName());
            model.addAttribute("pageInfo",     strPageInfo);
            
            return "user/review/reviewUpdate";
        }
    }
    
    // 후기 작성 페이지로 이동
    @GetMapping("/review_regist")
    public String reviewRegist(Model model, HttpSession session, @RequestParam("camp_no") int intCampNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        CampInfoVo objCampDetail = new CampInfoVo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
        } 
        
        objCampDetail = userCampService.getCampDetail(intCampNo); // 캠핑장 상세
        
        if (objCampDetail == null) {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "user/error";
            
        } else {
            model.addAttribute("campNo", intCampNo);
            model.addAttribute("userId", this.getLoginInfo(session).getUserName());
            model.addAttribute("camp",   objCampDetail);
            
            return "user/review/reviewRegist";
        }
    }
    
    // 캠핑장 상세(예약 작성)
    @GetMapping("/camp_detail_reservation")
    public String campDetailReservation(Model model, HttpSession session, @RequestParam("camp_no") int intCampNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        CampInfoVo objCampDetail = new CampInfoVo();
        
        if (this.getLoginInfo(session).getStateCode() == 0) {
            objResultInfo.setIntRetVal(9000);
            objResultInfo.setStrRetVal("로그인 이후 이용해주세요");
            
            model.addAttribute("resultInfo", objResultInfo);
            
            return "/user/error";
            
        } else {
            objCampDetail = userCampService.getCampDetail(intCampNo);
            
            if (objCampDetail == null) {
                objResultInfo.setIntRetVal(9999);
                objResultInfo.setStrRetVal("캠핑장 리스트 가져오는 중 에러");
                
                model.addAttribute("resultInfo", objResultInfo);
                
                return "/user/error";
            }
        }
        model.addAttribute("campNo",     intCampNo);
        model.addAttribute("camp",       objCampDetail);
        model.addAttribute("userId",     this.getLoginInfo(session).getUserName());
        
        return "user/camp/campDetailReservation";
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GetMapping("/date_test")
    public String dateTest(Model model, HttpSession session) {
    	
    	return "user/camp/dateTest";
    }
}
