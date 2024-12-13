function movePage(movePageNum) {
    
    let requestUrl = '/camping/user/my_review';
    
    let objQueryParam = {};

    objQueryParam.page  = movePageNum;
    objQueryParam.limit = 10;
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}