package com.team3.camping.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVo {

    private int    userNo;        // 번호
    private String userId;        // 아이디
    private String userPwd;       // 비밀번호
    private String userName;      // 이름
    private Date   userBirth;     // 생년월일
    
    private String gender;        // 성별
    private String userEmail;     // 이메일
    private String userTelNum;    // 전화번호
    private String zipCode;       // 우편 번호
    private String baseAddr;      // 기본 주소
    private String dtlAddr;       // 상세 주소
    
    private Date   regDate;       // 등록일
    private Date   updDate;       // 수정일
    private String reason;        // 사유
    private String adminId;       // 관리자
    private String userIdOrEmail; // 회원 아이디 or 이메일
    
    private int    count;         // 회원 수
    private String stateCode;     // 회원 상태
    private String secReason;     // 탈퇴사유
	private String regWeek;       // 주간 가입
	private String regMonth;      // 월간 가입
	
	private String userOriginPwd; // 기존 패스워드
	private String authVendor;
	private String platform;      // AUTH_VENDOR
	private String role;
	private int    rowNum;
	
	private String ageGroup;      // 연령 그룹
}   
