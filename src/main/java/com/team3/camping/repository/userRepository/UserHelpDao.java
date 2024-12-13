package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.HelpVo;

public interface UserHelpDao {
    
    // 문의 등록
    public Integer helpRegist(HelpVo objHelpVo);
    // 문의 리스트
    List<HelpVo> getHelpList(Map<String, Object> searchOptionMap);
    // 검색된 문의 총 갯수
    public Integer getHelpTotalCount(Map<String, Object> searchOptionMap);
    // 시퀀스 넘버 가져오기
    public Integer getSeqNo();
    // 문의 상세
    public HelpVo getHelp(int intHelpNo);

    
    // 문의 작성자 가져오기
    public String getHelpUserId(int intHelpNo);
    // 문의 상태코드 가져오기
    public String getStateCode(int intHelpNo);
    // 문의 삭제
    public Integer helpDelete(int intHelpNo);
}
