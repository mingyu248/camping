package com.team3.camping.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.UserRoles;
import com.team3.camping.repository.LoginDao;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomProviderService implements AuthenticationProvider, UserDetailsService {
	
	// private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginDao userLoginDao;

    // authenticate(인증/인가 메소드)에서 유저정보를 얻기 위해 사용된다
    @Override
	public CustomUser loadUserByUsername(String strUserName) {
    	
    	return userLoginDao.loadUserByUsername(strUserName);
    }
	
    // authenticate(인증/인가 메소드)에서 유저 권한 정보를 얻기 위해 사용된다
	private List<UserRoles> loadUserRole(String strUserName) {
		
		return userLoginDao.loadUserRole(strUserName);
	}
	
	// 상태코드 받기
	public int loadStateCode(String strUserName) {
		
		return userLoginDao.loadStateCode(strUserName);
	}
	
	// 인증 처리 메소드
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	    
	    System.out.println("=================[CustomProviderService][authenticate]================= : 실행");
		
		String strUserName = authentication.getName();
		String strPassword = "";

        CustomUser objUser = null;
        Collection<? extends GrantedAuthority> authorities = null;
        
        try {
	        	if (strUserName.trim().equals("")) {
	    			// throw new UsernameNotFoundException("회원 아이디를 입력하십시오.");
	        		throw new InternalAuthenticationServiceException("회원 아이디를 입력하십시오."); 
	        		// 대체 : Spring Security 5.7.x over
	    		}
	    		
	    		// 회원 여부 점검
	        	if (this.loadUserByUsername(strUserName) == null) {
	        		throw new UsernameNotFoundException("회원 아이디가 없습니다.");
	        	}
	        	
	        	objUser = this.loadUserByUsername(strUserName);
	            
	            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	            strPassword = (String) authentication.getCredentials(); // 비교할 비밀번호 
	            
	            if (passwordEncoder.matches(strPassword, objUser.getPassword())) 
	            	log.info("비밀번호 일치");	
	            else 
	            	throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
	            
	            List<UserRoles> roles = this.loadUserRole(strUserName);
	            objUser.setAuthorities(roles);
	            
	            authorities = objUser.getAuthorities();
	            
	            int stateCode = loadStateCode(strUserName);
	            
	            if (stateCode == 100) {
	                objUser.setEnabled(true);
	            } else if (stateCode == 900) {
	            	objUser.setEnabled(false);
	            	throw new DisabledException("정지된 아이디입니다.");
	            } else if (stateCode == 999) {
	                objUser.setEnabled(false);
                    throw new DisabledException("탈퇴된 아이디입니다.");
	            } else {
	                objUser.setEnabled(false);
                    throw new DisabledException("사용할 수 없는 아이디입니다");
	            }

        } catch(InternalAuthenticationServiceException e) { // 추가 : Spring Security 5.7.x over
        	log.info("[CustomUser][authenticate] 회원 아이디 미입력1");
            throw new InternalAuthenticationServiceException(e.getMessage());
            
        } catch(UsernameNotFoundException e) {
            log.info("[CustomUser][authenticate] 회원 아이디가 없음");
            // throw new UsernameNotFoundException(e.getMessage());
            throw new InternalAuthenticationServiceException(e.getMessage());
            
        } catch(BadCredentialsException e) {
            log.info("[CustomUser][authenticate] 패쓰워드가 잘못됨");
            throw new BadCredentialsException(e.getMessage());
            
        } catch(DisabledException e) {
        	log.info("[CustomUser][authenticate] 비활성화 상태의 유저");
        	throw new DisabledException(e.getMessage());
        	
        } catch(Exception e) {
        	
            log.info("[CustomUser][authenticate] 다른 종류의 에러 : " + e.toString());
            e.printStackTrace();
        }
        
        // SessionUser objSessionUser =  new SessionUser();

        return new UsernamePasswordAuthenticationToken(objUser, strPassword, authorities);
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
	    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}
}