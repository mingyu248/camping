package com.team3.camping.service.userService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;
import com.team3.camping.repository.userRepository.UserAccountDao;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    
    @Autowired
    private UserAccountDao userAccountDao;
    
    private final PlatformTransactionManager transactionManager;
    
    public UserAccountServiceImpl(PlatformTransactionManager transactionManager) {
          
        this.transactionManager = transactionManager;
    }
    
    // 아이디 중복 체크
    public ResultInfo userDuplicateCheck(String strUserId) {
        
        return userAccountDao.userDuplicateCheck(strUserId);
    }
    
    // 회원 정보 저장
    public ResultInfo userRegist(UserInfoVo objUser) {
        
        TransactionStatus txStatusUserRegist = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        ResultInfo objResultInfo;
        
        // 회원 정보 저장
        objResultInfo = userAccountDao.userInfoRegist(objUser);
        
        if (objResultInfo.getIntRetVal() != 0) {
            
            transactionManager.rollback(txStatusUserRegist);
            return objResultInfo;
        }
        
        // 회원 기록 저장
        objResultInfo = userAccountDao.userHisRegist(objUser);
        
        if (objResultInfo.getIntRetVal() != 0) {
            
            transactionManager.rollback(txStatusUserRegist);
            return objResultInfo;
        }
        
        // 회원 권한 저장
        objResultInfo = userAccountDao.userRoleRegist(objUser);
        
        if (objResultInfo.getIntRetVal() != 0) {
            
            transactionManager.rollback(txStatusUserRegist);
            return objResultInfo;
        }
        
        transactionManager.commit(txStatusUserRegist);
        
        return objResultInfo;
    }

    // 회원 정보 가져오기(자체로그인)
    @Override
    public UserInfoVo getUserInfo(String strUserKey) {
        
        return userAccountDao.getUserInfo(strUserKey);
    }
    
    // 회원 정보 가져오기(자체로그인)
    @Override
    public UserInfoVo getSocialUserInfo(String strUserKey) {
        
        return userAccountDao.getSocialUserInfo(strUserKey);
    }
    
    // 회원정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    @Override
    public ResultInfo userInfoUpdate(UserInfoVo objUser) {
        
        TransactionStatus txStatusUserInfoUpdate = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        ResultInfo objResultInfo = userAccountDao.userInfoUpdate(objUser);
        
        if (objResultInfo.getIntRetVal() != 0) {
            transactionManager.rollback(txStatusUserInfoUpdate);
        }
        transactionManager.commit(txStatusUserInfoUpdate);
        
        return objResultInfo;
    }
    
    // 패스워드 변경 시 기존 패스워드 체크
    @Override
    public String getPwd(String strUserKey) {
        
        // 기존 비밀번호 가져오기
        return userAccountDao.getPwd(strUserKey);
    }
    
    // 탈퇴 사유 리스트 가져오기
    @Override
    public List<SecReasonVo> getSecessionReasonList() {
        
        return userAccountDao.getSecessionReasonList();
    }
    
    // 회원 탈퇴
    @Override
    public ResultInfo userSecession(SecReasonVo objSecReason) {
        
        TransactionStatus txStatusUserSecession = transactionManager.getTransaction(new DefaultTransactionDefinition());
        
        ResultInfo objResultInfo = new ResultInfo();
        
        Integer intRetVal = userAccountDao.userSecession(objSecReason);
        
        if (intRetVal == null){
            transactionManager.rollback(txStatusUserSecession);
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("회원 탈퇴 중 에러 발생");
            
        } else if (intRetVal != 1) {
            transactionManager.rollback(txStatusUserSecession);
            
            objResultInfo.setIntRetVal(1320);
            objResultInfo.setStrRetVal("회원 탈퇴 실패");
            
        } else {
            
            if (objSecReason.getLoginState() == 2) {
                intRetVal = userAccountDao.SetUserTblStateCode(objSecReason);
            }
            
            if (intRetVal != 1) {
                transactionManager.rollback(txStatusUserSecession);
                
                objResultInfo.setIntRetVal(1420);
                objResultInfo.setStrRetVal("회원 탈퇴 실패");
            } else {
                transactionManager.commit(txStatusUserSecession);
                
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            }
        }
        return objResultInfo;
    }
}
