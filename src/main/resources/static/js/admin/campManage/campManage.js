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
					url: '/camping/admin/city_gun_gu_name',
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
	
	// 현제 사업주체 추출
	function getCurrentBusinessEntity() {
		const params = new URLSearchParams(window.location.search);
		
		return params.get('businessEntity') || '';
	}
	
	// 검색
	function searchHelp() {
		
		const businessEntity = $('#businessEntity').val();
		const facilityName   = $('#facilityName').val();
		const provinceName   = $('#provinceName').val();
		const cityGunGuName  = $('#cityGunGuName').val();
		const startDate      = $('#startDate').val();
		
		const endDate        = $('#endDate').val();
		const stateCode      = $('#stateCode').val();
		
		const limit = document.getElementById('limitSelect').value || 10;
		const page  = 1;
		
		console.log('businessEntity : ', businessEntity);
		console.log('facilityName : ', facilityName);
		console.log('provinceName : ', provinceName);
		console.log('cityGunGuName : ', cityGunGuName);
		console.log('startDate : ', startDate);
		console.log('endDate : ', endDate);
		console.log('stateCode : ', stateCode);
		
		var url = '/camping/admin/camp_manage?page=' + page + '&limit=' + limit;
		
		if (businessEntity !== '') {
			url += '&business_entity=' + encodeURIComponent(businessEntity);
		}
		
		if (facilityName !== '') {
			url += '&facility_name=' + encodeURIComponent(facilityName);
		}
		
		if (provinceName !== '') {
			url += '&province_name=' + encodeURIComponent(provinceName);
		}
		if (cityGunGuName !== '') {
			url += '&city_gun_gu_name=' + encodeURIComponent(cityGunGuName);
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
	
	// 최대 데이터
	function updateCampLimit() {
		
		var limit       = document.getElementById('limitSelect').value || 10;
		var currentPage = getCurrentPage();
		
		// const businessEntity = $('#businessEntity').val();
		const businessEntity = getCurrentBusinessEntity();
		const facilityName   = $('#facilityName').val();
		const provinceName   = $('#provinceName').val();
		const cityGunGuName  = $('#cityGunGuName').val();
		const startDate      = $('#startDate').val();
		
		const endDate        = $('#endDate').val();
		const stateCode      = $('#stateCode').val();
		
		var url = '/camping/admin/camp_manage?page=' + currentPage + '&limit=' + limit;
		
		if (businessEntity !== '') {
			url += '&business_entity=' + encodeURIComponent(businessEntity);
		}
		
		if (facilityName !== '') {
			url += '&facility_name=' + encodeURIComponent(facilityName);
		}
		
		if (provinceName !== '') {
			url += '&province_name=' + encodeURIComponent(provinceName);
		}
		if (cityGunGuName !== '') {
			url += '&city_gun_gu_name=' + encodeURIComponent(cityGunGuName);
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
	
	function campDetail(campNo) {
		
		location.href = '/camping/admin/camp_detail?no=' + campNo;
	}
	
	// 정지
	function suspensionCamp() {
		const campNo = $('#campNo').text();
		
		if (confirm('해당 캠핑장을 비활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/camp_deactivate',
				type:'POST',
				data: {
					no: campNo
				},
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
		console.log('Camp Number : ', campNo);
	}
	
	// 활성화
	function activateCamp() {
		
		const campNo = $('#campNo').text();
		
		if (confirm('해당 캠핑장을 활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/camp_activate',
				type:'POST',
				data: {
					no: campNo
				},
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 데이터 추가 팝업
	function openPopup() {
		
		var url = '/camping/admin/add_camp_info';
		window.open(url, "캠핑장 데이터 추가", "width=500, height=200, left=300, top=100");
	}
	
	// 업데이트 파일명
	function updateFileName() {
		
        const fileInput = document.getElementById('fileInput');
        const fileName  = document.getElementById('fileName');
        
        if (fileInput.files.length > 0) {
            fileName.value = fileInput.files[0].name; // 선택된 파일 이름 출력
        } else {
            fileName.value = ""; // 파일이 없을 때 텍스트 박스 비우기
        }
    }
    
    // 캠핑장 데이터 등록
    function registCampInfo() {
		
		const fileInput = document.getElementById('fileInput');
        const formData  = new FormData();
        
        console.log('File Name : ', fileInput);
        console.log('Form Data : ', formData);
        
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]); // 파일 추가
        } else {
            alert("파일을 선택해 주세요.");
            
            return;
        }
	}
	
	// 캠핑장 리뷰 상세
	function reviewDetail(reviewNo) {
		location.href = '/camping/admin/review_detail?no=' + reviewNo;
	}
	
	function stopAccountModal() {
		const reviewTitle = $('#reviewTitle').text();
		const reviewNo    = $('#reviewNo').text();
		
		$('#suspensionReviewNo').text(`번호 :  ${reviewNo}`);
		$('#suspensionReview').text(`제목 :  ${reviewTitle}`);
		openSuspensionModal();
	}
	
	// 모달 열기
	function openSuspensionModal() {
	    $('#suspensionModal').css('display', 'block'); // 정지 사유 모달을 보이게 설정
	}
	
	// 모달 닫기
	function closeSuspensionModal() {
	    $('#suspensionModal').css('display', 'none'); // 정지 사유 모달을 숨기기
	}
	
	// 리뷰 정지
	function stopReview() {
		
		const reviewNo = $('#reviewNo').text();
		const reason   = $('#suspensionReason').val();
		
		console.log('Review Number : ', reviewNo);
		console.log('Reason : ', reason);
		
		if (reason.trim() === '') {
			alert('정지 사유를 입력하세요.');
			return;
		} else {
			if (confirm('해당 후기를 정지 하시겠습니까?') == true) {
				$.ajax({
					url: '/camping/admin/stop_review',
					type: 'POST',
					contentType: "application/json",
					data: JSON.stringify({
						reviewNo: reviewNo
					   ,reason : reason
					}),
					success: function(result) {
						if (result.intRetVal == 0) {
							alert(result.strRetVal);
							location.reload();
						} else {
							alert(result.strRetVal);
						}
					},
					error: function(xhr) {
						alert('Server Error : ', xhr);
					}
				});
			} else {
				return;
			}
		}
	}
	
	// 활성화
	function activateCampReivew() {
		
		const reviewNo = $('#reviewNo').text();
		
		if (confirm('해당 후기를 활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/activate_review',
				type: 'POST',
				contentType: "application/json",
				data: JSON.stringify({
					reviewNo: reviewNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 삭제
	function deleteCampReview() {
		
		const campNo   = $('#campNo').val();
		const reviewNo = $('#reviewNo').text();
		
		if (confirm('해당 후기를 삭제 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/delete_review',
				type: 'POST',
				contentType: "application/json",
				data: JSON.stringify({
					reviewNo: reviewNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.href = '/camping/admin/camp_review?no=' + campNo;
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;	
		}
	}
	
	// 검색
	function searchReview() {
		
		const campNo      = $('#campNo').val();
		const reviewTitle = $('#reviewTitle').val();
		const userAccount = $('#userAccount').val();
		const startDate   = $('#startDate').val();
		const endDate     = $('#endDate').val();
		
		const stateCode   = $('#stateCode').val();
		
		const limit = 10;
		const page  = 1;
		
		var url = '/camping/admin/camp_review?no= + ' + campNo + '&page=' + page + '&limit=' + limit;
		
		if (reviewTitle !== '') {
			url += '&title=' + encodeURIComponent(reviewTitle);
		}
		
		if (userAccount !== '') {
			url += '&id=' + encodeURIComponent(userAccount);
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
	
	// 댓글 정지
	function stopComment(commentNo) {
		
		console.log('comment Number : ', commentNo);
		
		if (confirm('해당 댓글을 정지 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/stop_comment?no=' + commentNo,
				type: 'POST',
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 댓글 활성화
	function activateComment(commentNo) {
		
		if (confirm('해당 댓글을 활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/activate_comment?no=' + commentNo,
				type: 'POST',
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	// 댓글 삭제
	function deleteComment(commentNo) {
		
		if (confirm('해당 댓글을 제거 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/delete_comment?no=' + commentNo,
				type: 'POST',
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.reload();
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return;
		}
	}
	
	function reservationDetail(reservationNo) {
		console.log('userAccount : ', reservationNo);
		
		location.href = '/camping/admin/reservation_detail?no=' + reservationNo;
	}