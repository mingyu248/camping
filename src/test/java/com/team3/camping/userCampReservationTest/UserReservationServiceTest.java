package com.team3.camping.userCampReservationTest;

import static org.junit.jupiter.api.Assertions.*;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.service.userService.UserReservationService;

@SpringBootTest
class UserReservationServiceTest {

	@Autowired
	private UserReservationService userReservationService;
	
	@Test
	void userCampReservationTest() {
		
		ResultInfo    objResultInfo  = new ResultInfo();
		ReservationVo objReservation = new ReservationVo();
		
		try {
			String strCheckInDate = "24-12-07";
			String strCheckOutDate = "24-12-08";
			
			
			objReservation.setUserIdOrEmail("jaguar11789@naver.com");
			objReservation.setCampNo(2024000001);
			objReservation.setCampRoomNo(6);
			objReservation.setPeopleCount(4);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date utilCheckInDate = format.parse(strCheckInDate);
			java.sql.Date sqlCheckInDate = new java.sql.Date(utilCheckInDate.getTime());
			
			// 체크인 날짜 설정
			objReservation.setCheckInDate(sqlCheckInDate);
			
			// 체크아웃 날짜도 설정
			java.util.Date utilCheckOutDate = format.parse(strCheckOutDate);
			java.sql.Date sqlCheckOutDate = new java.sql.Date(utilCheckOutDate.getTime());
			objReservation.setCheckOutDate(sqlCheckOutDate);
			
			objResultInfo = userReservationService.userCampReservation(objReservation);
		} catch (Exception ex) {
			
		}
		assertEquals("zz", objResultInfo.getStrRetVal());
	}
}