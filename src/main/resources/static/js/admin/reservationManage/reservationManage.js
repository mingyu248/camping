/**
 * 
 */

	// 시/군/구 추출
	$(document).ready(function() {
		$('#provinceName').change(function() {
			var provinceName = $(this).val();
			console.log('Province Name : ', provinceName);
			if (provinceName) {
				$.ajax({
					url : '/camping/admin/city_gun_gu_name',
					type: 'GET',
					data: {
						province_name: provinceName
					},
					success: function(data) {
						var cityGunGuName = $('#cityGunGuName');
						cityGunGuName.empty();
						cityGunGuName.append('<option value="">시/군/구</option>');
						
						$.each(data, function(index, item) {
							cityGunGuName.append('<option value="' + item.cityGunGuName + '">' + item.cityGunGuName + '</option>');
						});
						
					},
					error: function(xhr) {
						alert('Server Error : ', xhr);
					}
				});
			} else {
				$('#cityGunGuName').empty().append('<option value="">시/군/구</option>');
			}
		});
	});
	
	// 현재 페이지 추출
	function getCurrentPage() {
		const params = new URLSearchParams(window.location.search);
		
		return params.get('page') ? parseInt(params.get('page')) : 1;
	}
	
	// 검색
	function searchReservation() {
		
		const userAccount    = $('#userAccount').val();
		const facilityName   = $('#facilityName').val();
		const provinceName   = $('#provinceName').val();
		const cityGunGuName  = $('#cityGunGuName').val();
		const startDate      = $('#startDate').val();
		
		const endDate        = $('#endDate').val();
		const stateCode      = $('#stateCode').val();
		
		const limit = document.getElementById('limitSelect').value || 10;
		const page  = 1;
		
		console.log('userAccount : ', userAccount);
		console.log('facilityName : ', facilityName);
		console.log('provinceName : ', provinceName);
		console.log('cityGunGuName : ', cityGunGuName);
		console.log('startDate : ', startDate);
		console.log('endDate : ', endDate);
		console.log('stateCode : ', stateCode);
		
		var url = '/camping/admin/reservation_manage?page=' + page + '&limit=' + limit;
		
		if (userAccount !== '') {
			url += '&user_account=' + encodeURIComponent(userAccount);
		}
		
		if (facilityName !== '') {
			url += '&facility=' + encodeURIComponent(facilityName);
		}
		
		if (provinceName !== '') {
			url += '&province=' + encodeURIComponent(provinceName);
		}
		if (cityGunGuName !== '') {
			url += '&city_gun_gu=' + encodeURIComponent(cityGunGuName);
		}
		if (startDate !== '') {
			url += '&start_date=' + encodeURIComponent(startDate);
		}
		
		if (endDate !== '') {
			url += '&end_date=' + encodeURIComponent(endDate);
		}
		
		if (stateCode !== '') {
			url += '&status=' + encodeURIComponent(stateCode);
		}
		window.location.href = url;
	}
	
	// 최대
	function updateReservationLimit() {
		
		var limit       = document.getElementById('limitSelect').value || 10;
		var currentPage = getCurrentPage();
		
		const userAccount    = $('#userAccount').val();
		const facilityName   = $('#facilityName').val();
		const provinceName   = $('#provinceName').val();
		const cityGunGuName  = $('#cityGunGuName').val();
		const startDate      = $('#startDate').val();
		
		const endDate        = $('#endDate').val();
		const stateCode      = $('#stateCode').val();
		
		var url = '/camping/admin/reservation_manage?page=' + currentPage + '&limit=' + limit;
		
		if (userAccount !== '') {
			url += '&user_account=' + encodeURIComponent(userAccount);
		}
		
		if (facilityName !== '') {
			url += '&facility=' + encodeURIComponent(facilityName);
		}
		
		if (provinceName !== '') {
			url += '&province=' + encodeURIComponent(provinceName);
		}
		if (cityGunGuName !== '') {
			url += '&city_gun_gu=' + encodeURIComponent(cityGunGuName);
		}
		if (startDate !== '') {
			url += '&start_date=' + encodeURIComponent(startDate);
		}
		
		if (endDate !== '') {
			url += '&end_date=' + encodeURIComponent(endDate);
		}
		
		if (stateCode !== '') {
			url += '&status=' + encodeURIComponent(stateCode);
		}
		window.location.href = url;
	}
	
	function reservationDetail(reservationNo) {
		console.log('userAccount : ', reservationNo);
		
		location.href = '/camping/admin/reservation_detail?no=' + reservationNo;
	}
	
	// 보류
	function holdReservation() {
		
		const reservationNo = $('#reservationNo').text();
		
		if (confirm('해당 예약정보를 보류 하시겠습니까?') == true) {
			$.ajax({
				url        : '/camping/admin/hold_reservation',
				type       : 'POST',
				contentType: "application/json",
				data       : JSON.stringify({
					       reservationNo: reservationNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function() {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 활성화
	function activateReservation() {
		
		const reservationNo = $('#reservationNo').text();
		
		console.log('reservationNo : ', reservationNo);
		
		if (confirm('해당 예약정보의 보류를 취소 하시겠습니까?') == true) {
			$.ajax({
				url        : '/camping/admin/activate_reservation',
				type       : 'POST',
				contentType: "application/json",
				data: JSON.stringify({
					      reservationNo: reservationNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function() {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 제거
	function deleteReservation() {
		
		const reservationNo = $('#reservationNo').text();
		
		if (confirm('해당 예약정보를 제거 하시겠습니까?') == true) {
			$.ajax({
				url        : '/camping/admin/delete_reservation',
				type       : 'POST',
				contentType: "application/json",
				data: JSON.stringify({
					      reservationNo: reservationNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.href = '/camping/admin/reservation_manage';
					} else {
						alert(result.strRetVal);
					}
				},
				error: function() {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}