package com.team3.camping.service.userService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.repository.userRepository.UserHelpDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserHelpServiceImpl implements UserHelpService {
    
    @Autowired
    private UserHelpDao userHelpDao;
    
    // 문의 등록
    @Override
    public ResultInfo helpRegist(HelpVo objHelpVo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        Integer intHelpNo = userHelpDao.getSeqNo();
        
        if (intHelpNo == null) {
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("시퀀스 가져오는 중 에러");
        }
        objHelpVo.setHelpNo(intHelpNo);
        
        Integer intRetVal = userHelpDao.helpRegist(objHelpVo);
        
        if (intRetVal == null) {
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("문의 작성 중 에러");
        }
        objResultInfo.setIntRetVal(0);
        objResultInfo.setStrRetVal("SUCCESS");
        objResultInfo.setIntHelpNo(intHelpNo);
        
        return objResultInfo;
    }
    
    // 문의 리스트
    @Override
    public PagingUtil getHelpList(Map<String, Object> searchOptionMap) {
        
        PagingUtil objPagingUtil = new PagingUtil();
        
        objPagingUtil.setCurrentPage((int)searchOptionMap.get("currentPage"));
        objPagingUtil.setLimit((int)searchOptionMap.get("limit"));
        
        try {
            searchOptionMap.put("startRow", objPagingUtil.getStartRow());
            searchOptionMap.put("endRow",   objPagingUtil.getEndRow());
            
            List<HelpVo> objHelpList = userHelpDao.getHelpList(searchOptionMap);
            Integer intHelpTotalCount = userHelpDao.getHelpTotalCount(searchOptionMap);
            
            if (objHelpList == null || intHelpTotalCount == null) {
                return null;
            }
            
            objPagingUtil.calculateTotalPages(intHelpTotalCount);
            searchOptionMap.put("totalPages", objPagingUtil.getTotalPages());
            
            objPagingUtil.setObjHelpList(objHelpList);
            
            objPagingUtil.setPageRange(objPagingUtil.getPageRange());
            searchOptionMap.put("pageRange", objPagingUtil.getPageRange());
            
        } catch (Exception ex) {
            log.info("==========[][getHelpList]==========");
            ex.printStackTrace();
            
            return null;
        }
        return objPagingUtil;
    }
    
    // 문의 상세
    @Override
    public HelpVo getHelp(int intHelpNo) {
        
        return userHelpDao.getHelp(intHelpNo);
    }
    
    // 문의 작성자 가져오기
    @Override
    public String getHelpUserId(int intHelpNo) {
        
        return userHelpDao.getHelpUserId(intHelpNo);
    }
    
    // 문의 상태코드 가져오기
    @Override
    public String getStateCode(int intHelpNo) {
        
        return userHelpDao.getStateCode(intHelpNo);
    }
    
    // 문의 삭제
    @Override
    public ResultInfo helpDelete(int intHelpNo) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        Integer intRetVal = userHelpDao.helpDelete(intHelpNo);
        
        if (intRetVal == null) {
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("문의 삭제 실패");
        } else {
            objResultInfo.setIntRetVal(0);
            objResultInfo.setStrRetVal("SUCCESS");
        }
        return objResultInfo;
    }
}
