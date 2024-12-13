package com.team3.camping.service.userService;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.PagingUtil;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.ReviewVo;
import com.team3.camping.repository.userRepository.UserReservationDao;


@Service
public class UserReservationServiceImpl implements UserReservationService {
	
	@Autowired
	private UserReservationDao userReservationDao;
	
	// 캠핑장 예약
	@Override
	public ResultInfo userCampReservation(ReservationVo objReservation) {

		return userReservationDao.userCampReservation(objReservation);
	}
    
    // 방 예약 상태 리스트
    @Override
    public List<ReservationVo> getReservationAvailableRoomList(int intCampNo, Date checkInDate, Date checkOutDate) {
        
        return userReservationDao.getReservationAvailableRoomList(intCampNo, checkInDate, checkOutDate);
    }
    
    // 내 예약 리스트
    @Override
    public List<ReservationVo> getMyReservationList(Map<String, Object> reservationPagingMap) {
        
        PagingUtil pagingUtil = new PagingUtil();
        
        pagingUtil.setCurrentPage((int)reservationPagingMap.get("currentPage"));
        pagingUtil.setLimit((int)reservationPagingMap.get("limit"));
        
        reservationPagingMap.put("startRow", pagingUtil.getStartRow());
        reservationPagingMap.put("endRow",   pagingUtil.getEndRow());
        
        List<ReservationVo> objReservationList  = userReservationDao.getMyReservationList(reservationPagingMap);
        int                 intTotalReview = userReservationDao.getMyTotalReservation(reservationPagingMap);
        
        pagingUtil.setTotalPages(intTotalReview);
        pagingUtil.calculateTotalPages(intTotalReview);
        
        reservationPagingMap.put("totalPages", pagingUtil.getTotalPages());
        reservationPagingMap.put("pageRange",  pagingUtil.getPageRange());

        return objReservationList;
    }
    
    // 예약 작성자
    @Override
    public String getReservationWriter(int intReservationNo) {
        
        return userReservationDao.getReservationWriter(intReservationNo);
    }
    
    // 예약 상세
    @Override
    public ReservationVo getReservationDetail(int intReservationNo) {
        
        return userReservationDao.getReservationDetail(intReservationNo);
    }
}
