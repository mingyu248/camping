package com.team3.camping.domain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVo {
	
	private int    reservationNo;     // 예약번호
	private int    campNo;            // 캠핑장 번호
	private String userIdOrEmail;     // 회원아이디 or 이메일
    private Date   checkInDate;       // 체크인 날짜
    private Date   checkOutDate;      // 체크아웃 날짜
	
	private Date   regDate;           // 등록일
	private int    peopleCount; 	  // 인원
	private String reservationDetail; // 예약 상세정보
	private Date   updDate;			  // 수정일
	private String adminId;			  // 관리자 아이디
	
	private String reason; 			  // 사유
	private String stateCode; 		  // 상태코드
    private int    count;             // 갯수
    private int    campRoomNo;        // 방 번호
    private int    reservationCount;  // 예약 갯수
    
    private String provinceName;
    private String cityGunGuName;
    private int    reservationHisNo;
    private String regWeek;
    
    private String regMonth;
    private String facilityName;      // 캠핑장 이름
    private int    reservationRowNo;  // 행 번호
}