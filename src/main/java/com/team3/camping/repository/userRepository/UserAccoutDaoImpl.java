package com.team3.camping.repository.userRepository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserAccoutDaoImpl implements UserAccountDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    private static final String MAPPER_PATH = "mapper.userMapper.UserMapper.";

    // 아이디 중복 체크
    public ResultInfo userDuplicateCheck(String strUserId) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            boolean blnRetVal = (int)sqlSession.selectOne(MAPPER_PATH + "userDuplicateCheck", strUserId) == 0;
            
            if (blnRetVal) {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            } else {
                objResultInfo.setIntRetVal(1100);
                objResultInfo.setStrRetVal("중복된 아이디");
            }
        
        } catch (Exception ex) {
            log.info("==========[UserDao][userDuplicateCheck]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("중복 체크 중 에러");
        }
        return objResultInfo;
    }
    
    // 회원 정보 저장
    public ResultInfo userInfoRegist(UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            boolean blnRetVal = (int)sqlSession.insert(MAPPER_PATH + "userInfoRegist", objUser) == 1;
            
            if (blnRetVal) {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            } else {
                objResultInfo.setIntRetVal(1120);
                objResultInfo.setStrRetVal("회원 정보 저장 중 에러");
            }
        
        } catch (Exception ex) {
            log.info("==========[UserDao][userInfoRegist]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("회원 정보 저장중 에러");
        }
        return objResultInfo;
    }
    
    // 회원 기록 저장
    public ResultInfo userHisRegist(UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();

        try {
            boolean blnRetVal = (int)sqlSession.insert(MAPPER_PATH + "userHisRegist", objUser.getUserId()) == 1;
            
            if (blnRetVal) {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            } else {
                objResultInfo.setIntRetVal(1320);
                objResultInfo.setStrRetVal("회원 기록 저장 실패");
            }
        
        } catch (Exception ex) {
            log.info("==========[UserDao][userHisRegist]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("회원 기록 저장중 에러");
        }
        return objResultInfo;
    }
    
    // 회원 권한 저장
    public ResultInfo userRoleRegist(UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            boolean blnRetVal = (int)sqlSession.insert(MAPPER_PATH + "userRoleRegist", objUser.getUserId()) == 1;
            
            if (blnRetVal) {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            } else {
                objResultInfo.setIntRetVal(1220);
                objResultInfo.setStrRetVal("회원 권한 저장 실패");
            }
        
        } catch (Exception ex) {
            log.info("==========[UserDao][userRoleRegist]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("회원 권한 저장중 에러");
        }
        return objResultInfo;
    }

    // 회원 정보 가져오기(자체로그인)
    @Override
    public UserInfoVo getUserInfo(String strUserKey) {
        
        UserInfoVo objUserInfo = new UserInfoVo();
        
        try {
            objUserInfo = sqlSession.selectOne(MAPPER_PATH + "getUserInfo", strUserKey);
            
        } catch (Exception ex) {
            log.info("==========[UserDao][getUserInfo]==========");
            ex.printStackTrace();
            
            objUserInfo = null;
        }
        return objUserInfo;
    }

    // 회원 정보 가져오기(소셜로그인)
    @Override
    public UserInfoVo getSocialUserInfo(String strUserKey) {
        
        UserInfoVo objUserInfo = new UserInfoVo();
        
        try {
            objUserInfo = sqlSession.selectOne(MAPPER_PATH + "getSocialUserInfo", strUserKey);
            
        } catch (Exception ex) {
            log.info("==========[UserDao][getSocialUserInfo]==========");
            ex.printStackTrace();
            
            objUserInfo = null;
        }
        return objUserInfo;
    }
    
    // 회원정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    @Override
    public ResultInfo userInfoUpdate(UserInfoVo objUser) {
        
        ResultInfo objResultInfo = new ResultInfo();
        
        try {
            boolean blnRetVal = (int)sqlSession.update(MAPPER_PATH + "userInfoUpdate", objUser) == 1;
            
            if (blnRetVal) {
                objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
            } else {
                objResultInfo.setIntRetVal(1130);
                objResultInfo.setStrRetVal("회원정보 수정 실패");
            }
        
        } catch (Exception ex) {
            log.info("==========[UserDao][userInfoUpdate]==========");
            ex.printStackTrace();
            
            objResultInfo.setIntRetVal(9999);
            objResultInfo.setStrRetVal("회원정보 수정중 에러");
        }
        return objResultInfo;
    }
    
    // 패스워드 가져오기
    @Override
    public String getPwd(String strUserKey) {
        
        String strRetVal = null;
        
        try {
            strRetVal = sqlSession.selectOne(MAPPER_PATH + "getPwd", strUserKey);
            
        } catch (Exception ex) {
            log.info("==========[UserDao][userDuplicateCheck]==========");
            ex.printStackTrace();
            
            strRetVal = null;
        }
        return strRetVal;
    }
    
    // 탈퇴 사유 리스트 가져오기
    @Override
    public List<SecReasonVo> getSecessionReasonList() {
        
        List<SecReasonVo> objSecResonList = null;
        
        try {
            objSecResonList = sqlSession.selectList(MAPPER_PATH + "getSecessionReasonList");
            
        } catch (Exception ex) {
            log.info("==========[UserDao][getSecessionReasonList]==========");
            ex.printStackTrace();
            
            objSecResonList = null;
        }
        
        return objSecResonList;
    }
    
    // 회원 탈퇴
    @Override
    public Integer userSecession(SecReasonVo objSecReason) {
        
        Integer intRetVal = null;

        try {
            intRetVal = sqlSession.insert(MAPPER_PATH + "userSecession", objSecReason);
            
        } catch (Exception ex) {
            log.info("==========[UserDao][userSecession]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        
        return intRetVal;
    }
    
    // 회원 탈퇴 User_Tbl 상태코드 변경
    @Override
    public Integer SetUserTblStateCode(SecReasonVo objSecReason) {
        
        Integer intRetVal = null;

        try {
            intRetVal = sqlSession.update(MAPPER_PATH + "SetUserTblStateCode", objSecReason);
            
        } catch (Exception ex) {
            log.info("==========[UserDao][SetUserTblStateCode]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        
        return intRetVal;
    }
    
}
