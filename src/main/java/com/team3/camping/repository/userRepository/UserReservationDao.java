package com.team3.camping.repository.userRepository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

public interface UserReservationDao {

	// 캠핑장 예약
	public ResultInfo userCampReservation(ReservationVo objReservation);
	
    // 예약된 방 번호 리스트 가져오기
    public List<ReservationVo> getReservationAvailableRoomList(int intCampRoomNo, Date checkInDate, Date checkOutDate);
    
    // 예약 리스트
    public List<ReservationVo> getMyReservationList(Map<String, Object> reservationPagingMap);

    // 검색된 예약 총 갯수
    public int getMyTotalReservation(Map<String, Object> reservationPagingMap);

    // 예약 상세
    public ReservationVo getReservationDetail(int intReservationNo);

    // 예약 작성자 가져오기
    public String getReservationWriter(int intReservationNo);
}
