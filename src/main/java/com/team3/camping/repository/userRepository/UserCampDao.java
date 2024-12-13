package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.CampInfoVo;

public interface UserCampDao {
    
    // 캠핑장 리스트
    public List<CampInfoVo> getCampList(Map<String, Object> searchOptionMap);
    // 캠핑장 상세(소개)
    public CampInfoVo getCampDetail(int intCampNo);
    // 캠핑장 이미지
    public List<String> getCampImgPathList(int intCampNo);
    // 검색된 캠핑장 총 갯수
    public Integer getCampTotalCount(Map<String, Object> searchOptionMap);
    // 인기 캠핑장 4개
    public List<CampInfoVo> getPopularCampList();

    
    // 조회수 증가
    public Integer campViewIncrease(int intCampNo);
}
