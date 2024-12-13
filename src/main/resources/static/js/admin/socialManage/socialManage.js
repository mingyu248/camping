/**
 * 
 */

	// 현재 페이지 추출
	function getCurrentPage() {
		const params = new URLSearchParams(window.location.search);
		
		return params.get('page') ? parseInt(params.get('page')) : 1;
	}

	// 검색
	function searchSocialAccount() {
		
		const userEmailOrId = $('#userEmailOrId').val();
		const platform      = $('#platform').val();
		const startDate     = $('#startDate').val();
		const endDate       = $('#endDate').val();
		const stateCode     = $('#stateCode').val();
		
		const limit = document.getElementById('limitSelect').value || 10;
		const page  = 1;
		
		console.log('User Email Or Id : ', userEmailOrId);
		console.log('Platform : ', platform);
		console.log('Start Date : ', startDate);
		console.log('EndDate : ', endDate);
		console.log('Status : ', stateCode);
		
		var url = '/camping/admin/social_manage?page=' + page + '&limit=' + limit;
		
		if (userEmailOrId !== '') {
			url += '&id=' + encodeURIComponent(userEmailOrId);
		}
		
		if (platform !== '') {
			url += '&platform=' + encodeURIComponent(platform);
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
	
	function updateSocialLimit() {
		
		var limit       = document.getElementById('limitSelect').value || 10;
		var currentPage = getCurrentPage();
		
		const userEmailOrId = $('#userEmailOrId').val();
		const platform      = $('#platform').val();
		const startDate     = $('#startDate').val();
		const endDate       = $('#endDate').val();
		const stateCode     = $('#stateCode').val();
		
		var url = '/camping/admin/social_manage?page=' + currentPage + '&limit=' + limit;
		
		if (userEmailOrId !== '') {
			url += '&id=' + encodeURIComponent(userEmailOrId);
		}
		
		if (platform !== '') {
			url += '&platform=' + encodeURIComponent(platform);
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
	
	function socialDetail(userNo) {
		
		location.href = '/camping/admin/social_detail?no=' + userNo;
	}
	
	function stopAccountModal() {
		const userEmail = $('#userEmail').text();
		$('#suspensionUserEmail').text(`사용자 계정 :  ${userEmail}`);
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
	
	// 정지
	function stopAccount() {
		
		const userEmail = $('#userEmail').text();
		const reason    = $('#suspensionReason').val();
		
		console.log('User Email : ', userEmail);
		console.log('Reason : ', reason);
		
		if (reason.trim() === '') {
			alert('정지 사유를 입력하세요.');
			return;
		} else {
			if (confirm('해당 계정을 비활성화 하시겠습니까?') == true) {
				$.ajax({
					url: '/camping/admin/stop_social',
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify({
						userEmail: userEmail
					   ,reason : reason
					}),
					success: function(result) {
						if (result.intRetVal == 0) {
							alert('해당 계정이 비활성화 되었습니다.');
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
	function activateAccount() {
		
		const userEmail = $('#userEmail').text();
		console.log('User Email : ', userEmail);
		
		if (confirm('해당 계정을 비활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/activate_social',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					userEmail: userEmail
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
	function deleteUserAccount() {
		
		const userEmail = $('#userEmail').text();
		console.log('User Email : ', userEmail);
		
		if (confirm('해당 계정을 비활성화 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/delete_social',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					userEmail: userEmail
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.href = '/camping/admin/social_manage';
					} else {
						alert(result.strRetVal + '\n' + result.intRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ', xhr);
				}
			});
		} else {
			return ;
		}
	}