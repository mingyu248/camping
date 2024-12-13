package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.CampInfoVo;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserCampDaoImpl implements UserCampDao {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.userMapper.CampMapper.";
	
	// 캠핑장 리스트 가져오기
	@Override
	public List<CampInfoVo> getCampList(Map<String, Object> searchOptionMap) {
	    
	    List<CampInfoVo> objCampList = null;
	    
	    try {
	        
	        objCampList = sqlSession.selectList(MAPPER_PATH + "getCampList", searchOptionMap);
        
        } catch (Exception ex) {
            log.info("==========[UserCampDao][getCampList]==========");
            ex.printStackTrace();
            
            objCampList = null;
        }
	    return objCampList;
	}
	
	// 검색된 캠핑장 총 갯수
    @Override
    public Integer getCampTotalCount(Map<String, Object> searchOptionMap) {
        
        Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "getCampTotalCount", searchOptionMap);
            
        } catch (Exception ex) {
            log.info("==========[UserCampDao][getCampTotalCount]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
    }
	
	// 캠핑장 상세(소개)
	@Override
	public CampInfoVo getCampDetail(int intCampNo) {
	    
	    CampInfoVo objCamp = null;
        
        try {
            objCamp = sqlSession.selectOne(MAPPER_PATH + "getCampDetail", intCampNo);
        
        } catch (Exception ex) {
            log.info("==========[UserCampDao][getCampDetail]==========");
            ex.printStackTrace();
            
            objCamp = null;
        }
        return objCamp;
	}
	
	// 캠핑장 조회수 증가
	@Override
	public Integer campViewIncrease(int intCampNo) {
	    
	    Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.update(MAPPER_PATH + "campViewIncrease", intCampNo);
        
        } catch (Exception ex) {
            log.info("==========[UserCampDao][campViewIncrease]==========");
            ex.printStackTrace();
            
            intRetVal = null;
        }
        return intRetVal;
	}
	
	// 캠핑장 이미지
	@Override
	public List<String> getCampImgPathList(int intCampNo) {
	    
	    List<String> strCampImgPathList = null;
        
        try {
            strCampImgPathList = sqlSession.selectList(MAPPER_PATH + "getCampImgPathList", intCampNo);
        
        } catch (Exception ex) {
            log.info("==========[UserCampDao][getCampImgPathList]==========");
            ex.printStackTrace();
            
            strCampImgPathList = null;
        }
        return strCampImgPathList;
	}
	
	// 인기 캠핑장 4개
	@Override
	public List<CampInfoVo> getPopularCampList() {
	    
        List<CampInfoVo> objCampList = null;
        
        try {
            objCampList = sqlSession.selectList(MAPPER_PATH + "getPopularCampList");
        
        } catch (Exception ex) {
            log.info("==========[UserCampDao][getPopularCampList]==========");
            ex.printStackTrace();
            
            objCampList = null;
        }
        return objCampList;
	}
}
