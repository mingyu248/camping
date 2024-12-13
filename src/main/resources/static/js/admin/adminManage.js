/**
 * 
 */
	// 현재 페이지 추출
	function getCurrentPage() {
		const params = new URLSearchParams(window.location.search);
		
		return params.get('page') ? parseInt(params.get('page')) : 1;
	}

	// 검색
	function searchAdmin() {
		
		var adminUserId = $('#adminUserId').val();
		var startDate   = $('#startDate').val();
		var endDate     = $('#endDate').val();
		var stateCode   = $('#stateCode').val();
		
		var limit = document.getElementById("limitSelect").value || 10;
		var page  = 1;
		
		console.log('Admin Id : ', adminUserId);
		console.log('Start Date : ', startDate);
		console.log('End Date : ', endDate);
		console.log('StateCode : ', stateCode);
		console.log('Limit : ', limit);
		
		var url = '/camping/admin/admin_manage?page=' + page + '&limit=' + limit;
		
		if (adminUserId !== '') {
			url += '&id=' + encodeURIComponent(adminUserId);
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
	function updateAdminLimit() {
		
		var limit       = document.getElementById("limitSelect").value || 10;
		var currentPage = getCurrentPage();
		
		var adminUserId   = $('#adminUserId').val();
		var startDate     = $('#startDate').val();
		var endDate       = $('#endDate').val();
		var stateCode     = $('#stateCode').val();
		
		console.log('Current Page : ', currentPage);
		
		var url = '/camping/admin/admin_manage?page=' + currentPage + '&limit=' + limit;
		
		if (adminUserId !== '') {
			url += '&id=' + encodeURIComponent(adminUserId);
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
	
	function adminDetail(adminNo) {
		console.log('Admin Number : ', adminNo);
		var url = '/camping/admin/admin_detail?no=' + adminNo;
		location.href = url;
	}
	
	function stopAccountModal() {
		const adminUserId = $('#adminUserId').text();
		$('#suspensionAdminUserId').text(`사용자 계정 :  ${adminUserId}`);
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
	
	// 계정 비활성화
	function stopAccount() {
		const adminUserId = $('#adminUserId').text();
		const reason  = $('#suspensionReason').val();
		
		if (reason.trim() === '') {
			alert('비활성화 이유를 입력하세요.');
			return;
		} else {
			if (confirm('해당 계정을 비활성화 하시겠습니까?') == true) {
				$.ajax({
					url: "/camping/admin/stop_account",
					type: "POST",
					contentType: "application/json",
					data: JSON.stringify({
						adminUserId: adminUserId
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
	
	// 계정 활성화
	function activateAccount() {
		const adminUserId = $('#adminUserId').text();
		console.log('Admin Id : ', adminUserId);
		
		if (confirm('해당 계정을 비활성화 하시겠습니까?') == true) {
			$.ajax({
				url: "/camping/admin/activate_account",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					adminUserId: adminUserId
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert('해당 계정이 활성화 되었습니다.');
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
	
	// 계정 삭제
	function deleteAccount() {
		
		const adminUserId = $('#adminUserId').text();
		
		if (confirm('해당 계정을 제거 하시겠습니까?') == true) {
			$.ajax({
				url: "/camping/admin/delete_account",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					adminUserId: adminUserId
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert('해당 계정이 제거 되었습니다.');
						location.href = "/camping/admin/admin_manage";
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
	
	function deleteUserAccount() {
		
		const adminUserId = $('#adminUserId').text();
		
		if (confirm('해당 계정을 제거 하시겠습니까?') == true) {
			$.ajax({
				url: "/camping/admin/delete_account",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify({
					adminUserId: adminUserId
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert('해당 계정이 제거 되었습니다.');
						location.href = "/camping/admin/user_manage";
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
	
	function checkPwd() {
		
	    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
	    
	    if (adminPwd.value === "") {
	        error[0].innerHTML     = "필수 정보입니다.";
	        error[0].style.display = "block";
	        
	    } else if (!pwPattern.test(adminPwd.value)) {
	        error[0].innerHTML           = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
	        pwMsg.innerHTML              = "사용불가";
	        pwMsgArea.style.paddingRight = "93px";
	        error[0].style.display       = "block";
	        
	        pwMsg.style.display = "block";
	        
	    } else {
	        error[0].style.display = "none";
	        pwMsg.innerHTML        = "안전";
	        pwMsg.style.display    = "block";
	        pwMsg.style.color      = "#03c75a";
	    }
	}
	
	function checkPwd() {
		if ($('#adminPwd').val().length < 9 || $('#adminPwd').val().length > 18) {
			alert('비밀번호 9자리이상 18자리 이하');
            $('#adminPwd').focus();
			$('#adminPwd').val('');
        }
	}
	
	function comparePwd() {
		
		const adminPwd = $('#adminPwd').val();
		const adminPwdCheck = $('#adminPwdCheck').val();
		
		console.log('Admin Password : ', adminPwd);
		console.log('Admin Password Check : ', adminPwdCheck);
		
		if (adminPwd != adminPwdCheck) {
			alert('비밀번호가 일치하지 않습니다.');
			$('#adminPwdCheck').focus();
			$('#adminPwdCheck').val('');
		}
	}
	
	function adminInfoUpdate() {
		
		const adminNo = $('#adminNo').text();
		const adminUserId = $('#adminUserId').text();
		const adminPwd = $('#adminPwd').val();
		const adminName = $('#adminName').val();
		const adminEmail = $('#adminEmail').val();
		
		const adminTelNum = $('#adminTelNum').val();
		const stateCode = $('#stateCode').val();
		
		const adminPwdCheck = $('#adminPwdCheck').val();
		
		console.log('Admin Number : ', adminNo);
		console.log('Admin Id : ', adminUserId);
		console.log('Admin Password : ', adminPwd);
		console.log('Admin Name : ', adminName);
		console.log('Admin Email : ', adminEmail);
		console.log('Admin TelNum : ', adminTelNum);
		
		if (adminPwd.trim() === '') {
			alert('비밀번호를 입력해주세요.');
			return;
		} else if (adminName.trim() === '') {
			alert('이름을 입력해주세요.');
			return;
		} else if (adminEmail.trim() === '') {
			alert('이메일을 입력해주세요.');
			return;
		} else if (adminTelNum.trim() === '') {
			alert('전화번호를 입력해주세요.');
			return;
		} else if (adminPwdCheck.trim() === '') {
			alert('비밀번호를 확인해주세요.');
			return;
		} else {
			if (confirm('해당 관리자 계정을 수정 하시겠습니까?') == true) {
				$.ajax({
					url: '/camping/admin/admin_update',
					type: 'POST',
					contentType: "application/json",
					data: JSON.stringify({
						userNo    : adminNo
					   ,userId    : adminUserId
					   ,userPwd   : adminPwd
					   ,userName  : adminName
					   ,userEmail : adminEmail
					   
					   ,userTelNum: adminTelNum
					   ,stateCode : stateCode
					}),
					success: function(result) {
						
						if (result.intRetVal == 0) {
							alert('해당 계정의 정보가 수정 되었습니다.');
							location.href = '/camping/admin/admin_detail?no=' + adminNo;
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