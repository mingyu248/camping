package com.team3.camping.service.userService;

import java.util.List;
import java.util.Map;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ResultInfo;

public interface UserCampService {

    // 캠핑장 리스트
    public PagingUtil getCampList(Map<String, Object> searchOptionMap);
    // 캠핑장 상세(소개)
    public CampInfoVo getCampDetail(int intCampNo);
    // 인기 캠핑장 4개
    public List<CampInfoVo> getPopularCampList();
    // 조회수 증가
    public ResultInfo campViewIncrease(int intCampNo);
}
