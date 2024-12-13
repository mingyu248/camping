package com.team3.camping.domain;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartVo {
	
	private int    cartNo;        		 // 카트 번호
	private int    campNo;        		 // 캠핑장 번호
    private String userIdOrEmail;  		 // 회원 아이디 or 이메일
    private Date   regDate;       		 // 등록일
    private String facilityName;  		 // 캠핑장 이름
    				
    private String facilityFeatures;     // 시설 특징
    private String facilityIntroduction; // 시설 소개
    private String cartImgPath; 		 // 이미지 경로
    private int    count;                // 갯수
    
    private String landAddress;          // 지번 주소
}
