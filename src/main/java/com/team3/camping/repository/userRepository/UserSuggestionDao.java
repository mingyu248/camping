package com.team3.camping.repository.userRepository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SuggestionVo;

public interface UserSuggestionDao {

    // 추천 중복체크
    public Integer suggestionCheck(SuggestionVo objSuggestion);
    // 추천 추가
    public ResultInfo addSuggestion(SuggestionVo objSuggestion);
    // 추천 삭제
    public Integer suggestionDelete(SuggestionVo objSuggestion);
    

}
