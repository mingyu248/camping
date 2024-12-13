package com.team3.camping.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.CustomUser;
import com.team3.camping.domain.UserRoles;
@Service
public class LoginService {

	public String getMainUrl() {
		
		String strMainPath = "main";
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof CustomUser) {
			CustomUser sessionUser = (CustomUser)principal;
            
            List<UserRoles> userRoles   = sessionUser.getAuthorities();
            for (UserRoles role : userRoles) {
                if(role.getRole().equals("ROLE_ADMIN")) {
                	strMainPath = "admin/adminMain";
                }
            }
		}
		else {
			strMainPath = "main";
		}
		return strMainPath;
	}
}
