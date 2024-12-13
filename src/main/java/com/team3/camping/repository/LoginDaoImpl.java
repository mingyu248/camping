package com.team3.camping.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.UserRoles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class LoginDaoImpl implements LoginDao {

	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.LoginMapper.";
	
	// 유저 정보 가져오기
	@Override
	public CustomUser loadUserByUsername(String strUserName) {
		
		CustomUser objUser = new CustomUser();
		log.info("===================[LoginDao][loadUserByUsername]===================== User Id : " + strUserName);
		
		try {
			objUser = sqlSession.selectOne(MAPPER_PATH + "loadUserByUsername", strUserName);
			
			log.info("===================[LoginDao][loadUserByUsername]===================== User Id : " + objUser.getUserId());
			
			return objUser;
			
		} catch (Exception ex) {
			log.info("===================[LoginDao][loadUserByUsername]===================== User Id Exception : " + ex.toString());
			// ex.printStackTrace();
			
			return null;
		}
	}

	// 유저 권한 가져오기
	@Override
	public List<UserRoles> loadUserRole(String strUserName) {
		
		try {
			return sqlSession.selectList(MAPPER_PATH + "loadUserRole", strUserName);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			return null;
		}
	}
	
	// 유저 상태코드 가져오기
	@Override
	public int loadStateCode(String strUserName) {
		
		try {
			return sqlSession.selectOne(MAPPER_PATH + "loadStateCode", strUserName);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			
			return -1;
		}
	}
	
	// 소셜 유저 상태코드 가져오기
	@Override
	public int loadSocialUserStateCode(String strUserEmail) {
	    
	    try {
            return sqlSession.selectOne(MAPPER_PATH + "loadSocialUserStateCode", strUserEmail);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            
            return -1;
        }
	}
}
