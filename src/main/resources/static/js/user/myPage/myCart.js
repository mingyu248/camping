// 페이지 이동
function movePage(movePageNum) {
    
    let requestUrl       = '/camping/user/my_cart';
    let objQueryParam    = {};

    objQueryParam.page   = movePageNum;
    objQueryParam.limit  = 10;
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}

// 찜 삭제
function cartDelet(campNo) {
    $.ajax({
        url: "/camping/user/cart_delete",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({campNo: campNo}),
        success: function(result) {
            if (result.intRetVal == 0) {
                location.reload();
                alert('해당 캠핑장이 찜에서 제거되었습니다');
            } else {
                alert(result.strRetVal);
            }
        },
        error: function() {
            alert('Server Error');
        }
    });
}