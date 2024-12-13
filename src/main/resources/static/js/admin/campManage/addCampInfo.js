/**
 * 
 */

	function updateFileName() {
		
        const fileInput = document.getElementById('fileInput');
        const fileName  = document.getElementById('fileName');
        
        if (fileInput.files.length > 0) {
            fileName.value = fileInput.files[0].name;
        } else {
            fileName.value = "";
        }
    }
    
    // 캠핑장 정보 등록
    function registCampInfo() {
		
		const fileInput = document.getElementById('fileInput');
        const formData  = new FormData();
        
        if (fileInput.files.length > 0) {
            formData.append('file', fileInput.files[0]); // 파일 추가
        } else {
            alert("파일을 선택해 주세요.");
            
            return;
        }
        $.ajax({
			url: '/camping/admin/regist_camp_info',
			method: 'POST',
			data: formData,
			processData: false,
			contentType: false,
			success: function(result) {
				if (result.intRetVal) {
					alert('성공 : ' + result.intSuccessCount + '\n실패 : ' + result.intFailedCount);
					window.opener.location.reload();
					window.close();
				} else {
					alert(result.strRetVal);
				}
			},
			error: function() {
				alert('Server Error');
			}
		});
	}