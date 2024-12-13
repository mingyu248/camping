package com.team3.camping.service.userService;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SuggestionVo;

public interface UserSuggestionService {

    // 추천 중복 체크
    public Integer suggestionCheck(SuggestionVo objSuggestion);
    // 추천 추가
    public ResultInfo addSuggestion(SuggestionVo objSuggestion);
    // 추천 삭제
    public ResultInfo suggestionDelete(SuggestionVo objSuggestion);
}
