package com.team3.camping.domain.naverAndGoogle;

import lombok.Data;
// import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

// @Getter
@Data // 변경
public class SessionUser implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal id;
	private String name;
    private String email;
    private String picture;

    // 추가
    private String authVendor;

    public SessionUser() {}
    
    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.authVendor = user.getAuthVendor(); // 추가
    }

}