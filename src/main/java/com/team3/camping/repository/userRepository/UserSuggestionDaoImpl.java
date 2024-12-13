package com.team3.camping.repository.userRepository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SuggestionVo;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserSuggestionDaoImpl implements UserSuggestionDao {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.userMapper.SuggestionMapper.";
	
	// 추천 중복체크
    @Override
    public Integer suggestionCheck(SuggestionVo objSuggestion) {
        
        Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "suggestionCheck", objSuggestion);
            
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            intRetVal = null;
        }
        return intRetVal;
    }
    
    // 추천 추가
    @Override
    public ResultInfo addSuggestion(SuggestionVo objSuggestion) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            if (sqlSession.insert(MAPPER_PATH + "addSuggestion", objSuggestion) == 0) {
                
                objResultInfo.setIntRetVal(5120);
                objResultInfo.setStrRetVal("추천 추가 안됨");
                
            } else {
                
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            }
            
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("추천 추가 중 에러");
        }
        return objResultInfo;
    }
    
    // 추천 삭제
    @Override
    public Integer suggestionDelete(SuggestionVo objSuggestion) {
        
        try {
            return sqlSession.insert(MAPPER_PATH + "suggestionDelete", objSuggestion);
                
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            return null;
        }
    }
}
