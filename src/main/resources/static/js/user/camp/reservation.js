	// 예약 페이지 이동
    function movePage(movePageNum) {
        
        let requestUrl = '/camping/user/my_reservation';
        
        let objQueryParam = {};
    
        // searchOption 은 campList.html 에서 초기화
        objQueryParam.page    = movePageNum;
        objQueryParam.limit   = 10;
        
        window.location.href  = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
    }
    
    function cancelReservation(reservationNo) {
    
    if (confirm('예약 취소 하시겠습니까?') == true) {
        $.ajax({
            url: '/camping/user/delete_reservation',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                reservationNo: reservationNo
            }),
            success: function(result) {
                if (result.intRetVal == 0) {
                    alert('예약 취소 되었습니다');
                    location.href = '/camping/user/my_reservation';
                } else {
                    alert(result.strRetVal);
                }
            },
            error: function () {
                alert('Server Error : ', xhr);
            }
        });
    } else {
        return;
    }
}   
    
	