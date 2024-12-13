package com.team3.camping.service.userService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.userRepository.UserCampDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserCampServiceImpl implements UserCampService {
    
    @Autowired
    private UserCampDao userCampDao;
    
    // 캠핑장 리스트
    @Override
    public PagingUtil getCampList(Map<String, Object> searchOptionMap) {
        
        PagingUtil objPagingUtil = new PagingUtil();
        
        objPagingUtil.setCurrentPage((int)searchOptionMap.get("currentPage"));
        objPagingUtil.setLimit((int)searchOptionMap.get("limit"));
        
        try {
            
            searchOptionMap.put("startRow", objPagingUtil.getStartRow());
            searchOptionMap.put("endRow",   objPagingUtil.getEndRow());
            
            List<CampInfoVo> objCampList = userCampDao.getCampList(searchOptionMap);
            
            if (objCampList == null) {
                return null;
            }
            
            Integer intCampTotalCount = userCampDao.getCampTotalCount(searchOptionMap);
            
            if (intCampTotalCount == null) {
                return null;
            }
            
            objPagingUtil.calculateTotalPages(intCampTotalCount);
            searchOptionMap.put("totalPages", objPagingUtil.getTotalPages());
            
            objPagingUtil.setObjCampList(objCampList);
            
            objPagingUtil.setPageRange(objPagingUtil.getPageRange());
            searchOptionMap.put("pageRange", objPagingUtil.getPageRange());
            
        } catch (Exception ex) {
            log.info("==========[][getCampList]==========");
            ex.printStackTrace();
            
            return null;
        }
        return objPagingUtil;
    }
    
    // 캠핑장 상세(소개)
    @Override
    public CampInfoVo getCampDetail(int intCampNo) {
        
        CampInfoVo objCampInfo = new CampInfoVo();
        List<String> strCampImgPathList = new ArrayList<>();
        
        objCampInfo        = userCampDao.getCampDetail(intCampNo);
        strCampImgPathList = userCampDao.getCampImgPathList(intCampNo);
        
        if (objCampInfo == null || strCampImgPathList == null) return null;
        
        objCampInfo.setCampImgPathList(strCampImgPathList);
        
        return objCampInfo;
    }
    
    // 조회수 증가
    @Override
    public ResultInfo campViewIncrease(int intCampNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        Integer    intRetVal     = userCampDao.campViewIncrease(intCampNo);
        
        if (intRetVal == 1) {
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("SUCCESS");
        } else {
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("조회수 증가 중 에러");
        }
        return objResultInfo;
    }
    
    // 인기 식단 4개
    @Override
    public List<CampInfoVo> getPopularCampList() {
        
        return userCampDao.getPopularCampList();
        
    }
}
