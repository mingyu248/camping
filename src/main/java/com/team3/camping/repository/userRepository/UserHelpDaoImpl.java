package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.HelpVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserHelpDaoImpl implements UserHelpDao {
    
    @Autowired
    private SqlSession sqlSession;
    
    private static final String MAPPER_PATH = "mapper.userMapper.HelpMapper.";
    
    // 시퀀스 넘버 가져오기
    @Override
    public Integer getSeqNo() {
        
        Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "getSeqNo");
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getSeqNo]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
        
    }
    
    // 문의 등록
    public Integer helpRegist(HelpVo objHelpVo) {
        
        Integer intRetVal = null;
        
        try {
            
            intRetVal = sqlSession.insert(MAPPER_PATH + "helpRegist", objHelpVo);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][helpRegist]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
    }
    
    // 문의 리스트
    @Override
    public List<HelpVo> getHelpList(Map<String, Object> searchOptionMap) {
        
        List<HelpVo> objHelpList = null;
        
        try {
            objHelpList = sqlSession.selectList(MAPPER_PATH + "getHelpList", searchOptionMap);
        
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getHelpList]==========");
            ex.printStackTrace();
            
            objHelpList = null;
        }
        return objHelpList;
    }
    
    // 검색된 문의 총 갯수
    @Override
    public Integer getHelpTotalCount(Map<String, Object> searchOptionMap) {
        
        Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "getHelpTotalCount", searchOptionMap);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getHelpTotalCount]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
    }
    
    // 문의 상세
    @Override
    public HelpVo getHelp(int intHelpNo) {
        
        HelpVo objHelpVo = null;
        
        try {
            objHelpVo = sqlSession.selectOne(MAPPER_PATH + "getHelp", intHelpNo);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getHelp]==========");
            ex.printStackTrace();
            
            objHelpVo = null;
        }
        return objHelpVo;
    }
    
    // 문의 작성자 가져오기
    @Override
    public String getHelpUserId(int intHelpNo) {
        
        String strRetVal = null;
        
        try {
            strRetVal = sqlSession.selectOne(MAPPER_PATH + "getHelpUserId", intHelpNo);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getHelpUserId]==========");
            ex.printStackTrace();
            
            strRetVal = null;
        }
        return strRetVal;
    }
    
    // 문의 상태코드 가져오기
    @Override
    public String getStateCode(int intHelpNo) {
        
        String strRetVal = null;
        
        try {
            strRetVal = sqlSession.selectOne(MAPPER_PATH + "getStateCode", intHelpNo);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][getStateCode]==========");
            ex.printStackTrace();
            
            strRetVal = null;
        }
        return strRetVal;
    }
    
    // 문의 삭제
    @Override
    public Integer helpDelete(int intHelpNo) {
        
        Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.insert(MAPPER_PATH + "helpDelete", intHelpNo);
            
        } catch (Exception ex) {
            log.info("==========[UserHelpDao][helpDelete]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
    }
    
}
