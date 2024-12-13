package com.team3.camping.repository;

import java.util.List;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.UserRoles;

public interface LoginDao {

    // 유저 정보 가져오기
	public CustomUser loadUserByUsername(String strUserName);
	
	// 유저 권한 가져오기
	public List<UserRoles> loadUserRole(String strUserName);
	
	// 유저 상태코드 가져오기
	public int loadStateCode(String strUserName);

	// 소셜 유저 상태코드 가져오기
    public int loadSocialUserStateCode(String strUserEmail);
}
