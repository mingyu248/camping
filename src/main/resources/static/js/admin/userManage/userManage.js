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
		
		var userId   = $('#userId').val();
		var startDate = $('#startDate').val();
		var endDate   = $('#endDate').val();
		var stateCode = $('#stateCode').val();
		
		var limit = document.getElementById("limitSelect").value || 10;
		var page  = 1;
		
		console.log('User Id : ', userId);
		console.log('Start Date : ', startDate);
		console.log('End Date : ', endDate);
		console.log('StateCode : ', stateCode);
		console.log('Limit : ', limit);
		
		var url = '/camping/admin/user_manage?page=' + page + '&limit=' + limit;
		
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
	function updateAdminLimit() {
		
		var limit       = document.getElementById("limitSelect").value || 10;
		var currentPage = getCurrentPage();
		
		var userId   = $('#userId').val();
		var startDate = $('#startDate').val();
		var endDate   = $('#endDate').val();
		var stateCode = $('#stateCode').val();
		
		console.log('Current Page : ', currentPage);
		
		var url = '/camping/admin/user_manage?page=' + currentPage + '&limit=' + limit;
		
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
	
	function userDetail(userNo) {
		console.log('Admin Number : ', userNo);
		var url = '/camping/admin/user_detail?no=' + userNo;
		location.href = url;
	}