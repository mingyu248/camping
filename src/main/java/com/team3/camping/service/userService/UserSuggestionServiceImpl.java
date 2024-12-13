package com.team3.camping.service.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SuggestionVo;
import com.team3.camping.repository.userRepository.UserSuggestionDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserSuggestionServiceImpl implements UserSuggestionService {
    
    @Autowired
    private UserSuggestionDao userSuggestionDao;
    
    private final PlatformTransactionManager transactionManager;
    
    public UserSuggestionServiceImpl(PlatformTransactionManager transactionManager) {
          
        this.transactionManager = transactionManager;
    }
    
    // 추천 중복체크
    @Override
    public Integer suggestionCheck(SuggestionVo objSuggestion) {
        return userSuggestionDao.suggestionCheck(objSuggestion);
    }
    
    // 추천 추가
    @Override
    public ResultInfo addSuggestion(SuggestionVo objSuggestion) {
        
        TransactionStatus txStatusAddSuggestion = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        try {
            ResultInfo objResultInfo = userSuggestionDao.addSuggestion(objSuggestion);
            
            if (objResultInfo.getIntRetVal() != 0) {
                transactionManager.rollback(txStatusAddSuggestion);
                
            } else {
                transactionManager.commit(txStatusAddSuggestion);
            }
            return objResultInfo;
            
        } catch (Exception ex) {
            transactionManager.rollback(txStatusAddSuggestion);
            log.info(ex.getMessage());
            
            return null;
        }
    }
    
    // 추천 삭제
    @Override
    public ResultInfo suggestionDelete(SuggestionVo objSuggestion) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        TransactionStatus txStatusSuggestionDelete = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        try {
            Integer intRetVal = userSuggestionDao.suggestionDelete(objSuggestion);
            
            if (intRetVal == null || intRetVal != 1) {
                transactionManager.rollback(txStatusSuggestionDelete);
                
                objResultInfo.setIntRetVal(5140);
                objResultInfo.setStrRetVal("추천 취소 실패");
                
            } else {
                transactionManager.commit(txStatusSuggestionDelete);
                
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            }
            return objResultInfo;
            
        } catch (Exception ex) {
            transactionManager.rollback(txStatusSuggestionDelete);
            
            log.info(ex.getMessage());
            objResultInfo.setIntRetVal(5140);
            objResultInfo.setStrRetVal("추천 취소 에러");
            
            return null;
        }
    }
}
