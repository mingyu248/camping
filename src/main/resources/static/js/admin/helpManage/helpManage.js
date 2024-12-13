/**
 * 
 */
	// 현재 페이지 추출
	function getCurrentPage() {
		const params = new URLSearchParams(window.location.search);
		
		return params.get('page') ? parseInt(params.get('page')) : 1;
	}
	
	// 검색
	function searchHelp() {
		
		const helpTitle = $('#helpTitle').val();
		const userId    = $('#userId').val();
		const startDate = $('#startDate').val();
		const endDate   = $('#endDate').val();
		const stateCode = $('#stateCode').val();
		
		const limit = document.getElementById("limitSelect").value || 10;
		const page  = 1;
		
		console.log('Help Title : ', helpTitle);
		console.log('User Id : ', userId);
		console.log('Start Date : ', startDate);
		console.log('End Date : ', endDate);
		console.log('StateCode : ', stateCode);
		
		var url = '/camping/admin/help_manage?page=' + page + '&limit=' + limit;
		
		if (helpTitle !== '') {
			url += '&title=' + encodeURIComponent(helpTitle);
		}
		
		if (userId !== '') {
			url += '&id=' + encodeURIComponent(userId);
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
	function updateHelpLimit() {
		
		var limit       = document.getElementById("limitSelect").value || 10;
		var currentPage = getCurrentPage();
		
		const helpTitle = $('#helpTitle').val();
		const userId    = $('#userId').val();
		const startDate = $('#startDate').val();
		const endDate   = $('#endDate').val();
		const stateCode = $('#stateCode').val();
		
		var url = '/camping/admin/help_manage?page=' + currentPage + '&limit=' + limit;
		
		if (helpTitle !== '') {
			url += '&title=' + encodeURIComponent(helpTitle);
		}
		
		if (userId !== '') {
			url += '&id=' + encodeURIComponent(userId);
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
	
	function helpDetail(helpNo) {
		console.log('Help Number : ', helpNo);
		location.href = '/camping/admin/help_detail?no=' + helpNo;
	}
	
	// 문의 답변
	function submitAnswer() {
		
		const helpNo     = $('#helpNo').text();
		const helpAnswer = $('#helpAnswer').val();
		
		console.log('Help Number : ', helpNo);
		console.log('Help Answer : ', helpAnswer);
		
		if (helpAnswer.trim() === '') {
			alert('답변 내용을 입력해주세요.');
			return;
		} else {
			if (confirm('아래 내용으로 답변을 제출 하시겠습니까?') == true) {
				$.ajax({
					url: '/camping/admin/help_answer',
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify({
						helpNo: helpNo
					   ,answer : helpAnswer
					}),
					success: function(result) {
						if (result.intRetVal == 0) {
							alert('문의 답변을 제출 하였습니다.');
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
	
	// 문의 삭제
	function deleteAnswer() {
		const helpNo     = $('#helpNo').text();
		console.log('Help Number : ', helpNo);
		
		if (confirm('해당 문의를 삭제 하시겠습니까?') == true) {
			$.ajax({
				url: '/camping/admin/help_delete',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					helpNo: helpNo
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert('해당 문의가 삭제 되었습니다.');
						location.href = '/camping/admin/help_manage';
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