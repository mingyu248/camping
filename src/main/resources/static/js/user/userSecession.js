function openPopup() {
    
    var popupURL = "/camping/user/secession";
    var width = 450;  // 팝업 너비
    var height = 500; // 팝업 높이

    // 화면의 중앙에 위치 계산
    var left = (screen.width / 2) - (width / 2);
    var top = (screen.height / 2) - (height / 2);

    window.open(popupURL, "Secession", `width=${width},height=${height},left=${left},top=${top}`);
}

function closePage() {
    
    window.close(); 
    
}


function userSecession() {
    
    const secReasonNo = $("input[name='reason']:checked").val();
        
        if(!secReasonNo) {
             alert("탈퇴 사유를 선택해 주세요.")
             return;
         }
        
        if(confirm("정말 회원탈퇴를 진행하시겠습니까?") == true) {
             
             $.ajax({
                 url:'/camping/user/secession',
                 type: 'POST',
                 contentType: 'application/json',
                 data: JSON.stringify({
                     secReasonNo: secReasonNo
                 }),
                 success: function (result) {
                     if (result.intRetVal == 0) {
                        alert('정상적으로 탈퇴되었습니다.');
                        window.close();
                        /* 부모창 페이지 이동*/
                        opener.location.href = "/camping/logout"
                        
                     } else {
                         alert(result.strRetMsg);
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
