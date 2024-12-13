package com.team3.camping.domain;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private String userId;  // 회원 아이디
    private String userPwd; // 회원 비밀번호
 
    /* Spring Security related fields */
    private List<UserRoles> authorities;
    private boolean accountNonExpired     = true;
    private boolean accountNonLocked      = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled               = true;

    @Override
    public String getPassword() {
        return this.userPwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }
    
}