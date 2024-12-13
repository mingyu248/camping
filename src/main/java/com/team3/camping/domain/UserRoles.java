package com.team3.camping.domain;

import org.springframework.security.core.GrantedAuthority;

public class UserRoles implements GrantedAuthority {

	private static final long serialVersionUID = 7464267597005842862L;
	
	private int    userRoleNum; // ROLE 번호
	private String userId;      // 회원 아이디
	private String role;        // 회원 권한
	
	public int getUserRoleNum() {
		
        return userRoleNum;
    }

    public void setUserRoleNum(int userRoleNum) {
    	
        this.userRoleNum = userRoleNum;
    }

    public String getUserId() {
    	
        return userId;
    }

    public void setUserId(String userId) {
    	
        this.userId = userId;
    }

    public String getRole() {
    	
        return role;
    }

    public void setRole(String role) {
    	
        this.role = role;
    }

    public static long getSerialversionuid() {
    	
        return serialVersionUID;
    }

	@Override
	public String getAuthority() {
    	
		return this.role;
	}
}