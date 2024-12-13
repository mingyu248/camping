package com.team3.camping.domain;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampInfoVo {

	private int    campNo;                      // 캠핑장 번호
	private String facilityName;                // 시설명
	private String campingType;                 // 캠핑 유형
	private String provinceName;                // 시도 명칭
	private String cityGunGuName;               // 시군구 명칭
	
	private String legalTownVillageDongName;    // 법정읍면동명칭
	private String riName;                      // 리 명칭
	private String lotNumber;                   // 번지
	private String roadName;                    // 도로명 이름
	private String buildingNumber;              // 건물번호
	
	private double latitude;                    // 위도
	private double longitude;                   // 경도
	private String postalCode;                  // 우편번호
	private String roadAddress;                 // 도로명 주소
	private String landAddress;                 // 지번 주소

	private String telNumber;                   // 전화번호
	private String website;                     // 홈페이지
	private String businessEntity;              // 사업주체
	private char   weekdayOperation;            // 평일 운영 여부
	private char   weekendOperation;            // 주말 운영 여부
	
	private char   springOperation;             // 봄 운영 여부
	private char   summerOperation;             // 여름 운영 여부
	private char   autumnOperation;             // 가을 운영 여부
	private char   winterOperation;             // 겨울 운영 여부
	private char   electricitySubfacility;      // 부대시설 전기
	
	private char   hotWaterSubfacility;         // 부대시설 온수
	private char   wirelessInternetSubfacility; // 부대시설 유무선 인터넷
	private char   firewoodSalesSubfacility;    // 부대시설 장작판매
	private char   walkingTrailSubfacility;     // 부대시설 산책로
	private char   waterPlayAreaSubfacility;    // 부대시설 물놀이장
	
	private char   playgroundSubfacility;       // 부대시설 놀이터
	private char   martSubfacility;             // 부대시설 마트
	private int    numberOfToilets;             // 부대시설 화장실 수
	private int    numberOfShowers;             // 부대시설 샤워실 수
	private int    numberOfSinks;               // 부대시설 씽크대 수

	private int    numberOfFireExtinguishers;   // 부대시설 소화기 수
	private char   bedGlamping;                 // 글램핑 침대
	private char   tVGlamping;                  // 글램핑 티비
	private char   refrigeratorGlamping;        // 글램핑 냉장고
	private char   wiredInternetGlamping;       // 글램핑 유무선 인터넷
	
	private char   internalToiletGlamping;      // 글램핑 내부화장실
	private char   airConditionerGlamping;      // 글램핑 에어컨
	private char   heatingDeviceGlamping;       // 글램핑 난방기구
	private char   cookingUtensilsGlamping;     // 글램핑 취사도구
	private String facilityFeatures;            // 시설 특징

	private String facilityIntroduction;        // 시설 소개
	private Date   lastUpdatedDate;             // 최종작성일
	private String StateCode;                   // 상태코드 - 100 : 정상, 500 : 비공개
	private Date   updDate;                     // 최근 상태 변경일
	private int    count;                       // 캠핑장 수
	
	private String imagePath;                   // 캠핑장 이미지 프로젝트 경로(리스트 페이지)
	private int    suggestion;                  // 캠핑장 추천 수
	private int    views;                       // 조회수
	private int    review;                      // 리뷰수
	private boolean cartCheck;                  // 카트체크
	
	private boolean suggestionCheck;            // 추천체크
	
	private List<String> campImgPathList;       // 캠핑장 이미지 경로 리스트
}
