package com.team3.camping.repository.userRepository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.CartVo;
import com.team3.camping.domain.ResultInfo;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserCartDaoImpl implements UserCartDao {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.userMapper.CartMapper.";
	
	// 찜 추가
	@Override
	public ResultInfo addCart(CartVo objCart) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			if (sqlSession.insert(MAPPER_PATH + "addCart", objCart) == 0) {
				
				objResultInfo.setIntRetVal(7120);
				objResultInfo.setStrRetVal("찜 추가 안됨");
				
				return objResultInfo;
				
			} else {
			    
			    objResultInfo.setIntRetVal(0);
                objResultInfo.setStrRetVal("SUCCESS");
                
				return objResultInfo;
			}
			
		} catch (Exception ex) {
			log.info(ex.getMessage());
			
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("찜 추가 중 에러");
			
			return objResultInfo;
		}
	}
	
	// 찜 리스트
	@Override
	public List<CartVo> getCartList(Map<String, Object> cartPagingMap) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			List<CartVo> objCartList = sqlSession.selectList(MAPPER_PATH + "getCartList", cartPagingMap);
			
			return objCartList;
			
		} catch (Exception ex) {
			log.info("======[UserCartDao][getCartList]======");
			objResultInfo.setIntRetVal(9999);
			objResultInfo.setStrRetVal("리스트 출력 중 에러");
			
			return null;
		}
	}
	
	// 찜 중복 체크
	@Override
	public Integer cartCheck(CartVo objCart) {
	    
	    Integer intRetVal = null;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "cartCheck", objCart);
            
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            intRetVal = null;
        }
        return intRetVal;
	}
	
	// 카트 삭제
	@Override
	public Integer cartDelete(CartVo objCart) {
	    
        try {
            return sqlSession.insert(MAPPER_PATH + "cartDelete", objCart);
                
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            return null;
        }
	}
	
	// 카트 총 갯수
	@Override
	public Integer getCampTotalCount(Map<String, Object> cartPagingMap) {
	    
	    try {
            return sqlSession.selectOne(MAPPER_PATH + "getCampTotalCount", cartPagingMap);
                
        } catch (Exception ex) {
            log.info(ex.getMessage());
            
            return null;
        }
	}
}
