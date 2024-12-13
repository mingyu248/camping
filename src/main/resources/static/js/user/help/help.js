function helpRegist() {
    
    let helpTitle  = $('#helpTitle').val();
    let helpDetail = $('#helpDetail').val();
    
    if (helpTitle.trim() == '') {
        alert('제목을 입력해주세요');
        return;
    }
    if (helpDetail.trim() == '') {
        alert('내용을 입력해주세요');
        return;
    }
    
    if (confirm("문의를 제출하시겠습니까?")) {
    
        $.ajax({
            url:'/camping/user/help_regist',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                helpTitle,
                helpDetail
            }),
            success: function (result) {
                if (result.intRetVal == 0) {
                    alert('문의가 등록 되었습니다.');
                    window.location.href = "/camping/user/help_detail?help_no=" + result.intHelpNo;
                } else {
                    alert(result.strRetVal);
                }
            },
            error: function () {
                alert('ERROR');
            }   
        });
    } else {
        return;
    }
}

// 문의 검색 URL 생성, GET 요청
function search() {
    
    let requestUrl = '/camping/user/my_help';
    
    let objQueryParam = {};

    //objQueryParam.page = 1;
    if ($('#limit').val() != '')     objQueryParam.limit      = $('#limit').val();
    if ($('#searchKey').val() != '') objQueryParam.help_title = $('#searchKey').val().trim();
    if ($('#stateCode').val() != '') objQueryParam.state_code = $('#stateCode').val();
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}

// 문의 페이지 이동
function movePage(movePageNum) {
    
    let requestUrl = '/camping/user/my_help';
    
    let objQueryParam = {};

    // searchOption 은 campList.html 에서 초기화
    objQueryParam.page = movePageNum;
    
    if (searchOption.limit != null)      objQueryParam.limit      = searchOption.limit;
    if (searchOption.helpTitle != null) objQueryParam.help_title = searchOption.helpTitle;
    if (searchOption.stateCode != null)  objQueryParam.state_code = searchOption.stateCode;
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}

// 문의 삭제
function helpDelete(helpNo) {
    
    if (confirm("문의를 삭제하시겠습니까?")) {
        $.ajax({
            url:'/camping/user/help_delete',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(helpNo),
            success: function (result) {
                if (result.intRetVal == 0) {
                    alert('문의가 삭제 되었습니다.');
                    window.location.href = '/camping/user/my_help';
                } else {
                    alert(result.strRetVal);
                }
            },
            error: function () {
                alert('ERROR');
            }   
        });
    } else {
        return;
    }
}