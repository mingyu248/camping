package com.team3.camping.repository.userRepository;


import java.sql.Types;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.team3.camping.domain.ReservationVo;
import com.team3.camping.domain.ResultInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class UserReservationDaoImpl implements UserReservationDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlSession sqlSession;
	
	private static final String MAPPER_PATH = "mapper.userMapper.ReservationMapper.";

	@Override
	public ResultInfo userCampReservation(ReservationVo objReservation) {
		
		ResultInfo objResultInfo = new ResultInfo();
		
		try {
			SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
						                 .withProcedureName("USER_RESERVATION_CAMP")
						                 .declareParameters(
						                   		            new SqlParameter("pi_intCampNo",      Types.INTEGER)
						                   		           ,new SqlParameter("pi_strUserAccount", Types.VARCHAR)
						                   		           ,new SqlParameter("pi_intCampRoomNo",  Types.INTEGER)
						                   		           ,new SqlParameter("pi_dtCheckInDate",  Types.DATE)
						                   		           ,new SqlParameter("pi_dtCheckOutDate", Types.DATE)

						                   		           ,new SqlParameter("pi_intPeopleCount", Types.INTEGER)
						                   		            
						                   		           ,new SqlOutParameter("po_intRetVal",   Types.INTEGER)
						                    		       ,new SqlOutParameter("po_strRetVal",   Types.VARCHAR)
						                   		            );

			Map<String, Object> inputParameter = new HashMap<>();
			
			inputParameter.put("pi_intCampNo",      objReservation.getCampNo());
			inputParameter.put("pi_strUserAccount", objReservation.getUserIdOrEmail());
			inputParameter.put("pi_intCampRoomNo",  objReservation.getCampRoomNo());
			inputParameter.put("pi_dtCheckInDate",  objReservation.getCheckInDate());
			inputParameter.put("pi_dtCheckOutDate", objReservation.getCheckOutDate());

			inputParameter.put("pi_intPeopleCount", objReservation.getPeopleCount());
			
			Map<String, Object> outputParameter = jdbcCall.execute(inputParameter);
			
			int    intRetVal = (int)    outputParameter.get("po_intRetVal");
			String strRetVal = (String) outputParameter.get("po_strRetVal");
			
			objResultInfo.setIntRetVal(intRetVal);
			objResultInfo.setStrRetVal(strRetVal);

		} catch (Exception ex) {
			objResultInfo.setIntRetVal(9990);
			objResultInfo.setStrRetVal("============[AdminReviewMngDao][userReviewDeactivate] Exception : " + ex.getMessage());
		}
		return objResultInfo;
	}
	
	// 예약된 방 번호 리스트 가져오기
	@Override
	public List<ReservationVo> getReservationAvailableRoomList(int intCampNo, Date checkInDate, Date checkOutDate) {
	    
	    List<ReservationVo> objReservationList = null;
	    
	    Map<String, Object> dateInfoMap = new HashMap<>();
	    
	    dateInfoMap.put("campNo",       intCampNo);
	    dateInfoMap.put("checkInDate",  checkInDate.toString());
	    dateInfoMap.put("checkOutDate", checkOutDate.toString());
	    
        try {
            objReservationList = sqlSession.selectList(MAPPER_PATH + "getReservationAvailableRoomList", dateInfoMap);
                    
        } catch (Exception ex) {
            log.info("======================[UserReservationDao][getReservationAvailableRoomList]====================== Exception : " + ex.getMessage());
            
            objReservationList = null;
        }
	    return objReservationList;
	}
	
	// 내 예약 리스트
	@Override
	public List<ReservationVo> getMyReservationList(Map<String, Object> reservationPagingMap) {
	    
	    List<ReservationVo> objReservationList = null;
        
        try {
            objReservationList = sqlSession.selectList(MAPPER_PATH + "getMyReservationList", reservationPagingMap);
                    
        } catch (Exception ex) {
            log.info("======================[UserReservationDao][getMyReservationList]======================");
            ex.printStackTrace();
            
            objReservationList = null;
        }
        return objReservationList;
	}
	
	// 검색된 예약 총 갯수
	@Override
	public int getMyTotalReservation(Map<String, Object> reservationPagingMap) {
	    
        int intRetVal = 0;
        
        try {
            intRetVal = sqlSession.selectOne(MAPPER_PATH + "getMyTotalReservation", reservationPagingMap);
            
        } catch (Exception ex) {
            log.info("======[UserReviewDao][getMyTotalReservation]======");
            ex.printStackTrace();
            
            intRetVal = 0;
        }
        return intRetVal;
	}
	
	// 예약 작성자 가져오기
	@Override
	public String getReservationWriter(int intReservationNo) {
	    
	    String strReservationWriter = null;
        
        try {
            strReservationWriter = sqlSession.selectOne(MAPPER_PATH + "getReservationWriter", intReservationNo);
                    
        } catch (Exception ex) {
            log.info("======================[UserReservationDao][getReservationWriter]======================");
            ex.printStackTrace();
            
            strReservationWriter = null;
        }
        return strReservationWriter;
	}
	
	// 예약 상세
	@Override
	public ReservationVo getReservationDetail(int intReservationNo) {
	    
	    ReservationVo objReservation = null;
        
        try {
            objReservation = sqlSession.selectOne(MAPPER_PATH + "getReservationDetail", intReservationNo);
                    
        } catch (Exception ex) {
            log.info("======================[UserReservationDao][getReservationDetail]======================");
            ex.printStackTrace();
            
            objReservation = null;
        }
        return objReservation;
	}
	
	
	
}
