package com.team3.camping.service.userService;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

public interface UserReservationService {

	// 캠핑장 예약
	public ResultInfo userCampReservation(ReservationVo objReservation);
	
    // 방 예약 상태 리스트
    List<ReservationVo> getReservationAvailableRoomList(int intCampNo, Date checkInDate, Date checkOutDate);
    
    // 내 예약 리스트
    public List<ReservationVo> getMyReservationList(Map<String, Object> reservationPagingMap);

    // 예약 상세
    public ReservationVo getReservationDetail(int intReservationNo);

    // 예약 작성자 가져오기
    public String getReservationWriter(int intReservationNo);
}
