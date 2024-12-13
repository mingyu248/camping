package com.team3.camping.controller.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.team3.camping.domain.CampInfoVo;
import com.team3.camping.domain.ReservationVo;
import com.team3.camping.service.adminService.AdminStatisticsMngService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminStatisticsMngRestController {

	@Autowired
	private AdminStatisticsMngService adminStatisticsMngService;
	
	// 시/군/구 별 캠핑장 현황
	@GetMapping("/chart_by_province")
	public ResponseEntity<List<CampInfoVo>> getCampCityGunGuStatisticsByProvince(@RequestParam(name = "provinceName") String strProvinceName) {
		
		List<CampInfoVo> objCampCityGunGuStatistics = adminStatisticsMngService.getCampStatisticsByCityGunGu(strProvinceName);
		
		return ResponseEntity.ok(objCampCityGunGuStatistics); 
	}
	
	// 시/군/구 별 예약 현황
	@GetMapping("/reservation_by_province")
	public ResponseEntity<List<ReservationVo>> getReservationCityGunGuStatisticsByProvince(@RequestParam(name = "provinceName") String strProvinceName) {
		
		log.info("===================================[AdminStatisticsMngRestController][getReservationCityGunGuStatisticsByProvince]=================================== provinceName : "  + strProvinceName);
		
		List<ReservationVo> objReservationCityGunGuStatistics = adminStatisticsMngService.getReservationStatisticsByCityGunGu(strProvinceName);
		
		return ResponseEntity.ok(objReservationCityGunGuStatistics);
		
	}
}
