package com.team3.camping.service.userService;

import java.util.Map;

import com.team3.camping.domain.HelpVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;

public interface UserHelpService {
    
    // 문의 등록
    public ResultInfo helpRegist(HelpVo objHelpVo);
    // 문의 리스트
    public PagingUtil getHelpList(Map<String, Object> searchOptionMap);
    // 문의 상세
    public HelpVo getHelp(int intHelpNo);
    // 문의 작성자 가져오기
    public String getHelpUserId(int intHelpNo);
    // 문의 상태코드 가져오기
    public String getStateCode(int intHelpNo);

    
    // 문의 삭제
    public ResultInfo helpDelete(int intHelpNo);
}
