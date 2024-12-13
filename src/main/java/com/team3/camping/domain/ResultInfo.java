package com.team3.camping.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo {

	private int    intRetVal       = 9999;
	private String strRetVal       = "ERROR";
	private int    intHelpNo;           // 문의 번호
	private int    intReviewNo;         // 후기 번호
	private int    intCampNo;           // 캠핑장 번호
	
	private String strReservationNo;    // 예약 번호
	private String strPageInfo;         // 페이지 정보(캠핑장 페이지 or 마이페이지)
	private String strRedirectUrl;      // 리다이렉트 주소
	private int    intSuccessCount = 0;
	private int    intFailedCount  = 0;
}
