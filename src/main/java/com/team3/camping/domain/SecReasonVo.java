package com.team3.camping.domain;

import com.team3.camping.domain.SecReasonVo;

import lombok.Data;

@Data
public class SecReasonVo {
    
    private String userId;      // 유저 아이디
    private String secReason;   // 탈퇴 사유
    private int    secReasonNo; // 사유 번호
    private int    loginState;  // 로그인 상태코드 0:로그아웃, 1:자체로그인, 2:소셜로그인

}
