/**
 * 
 */
	var userId       = document.querySelector('#userId');
	var userPwd      = document.querySelector('#userPwd');
	var pwMsg        = document.querySelector('#alertTxt');
	var pwImg1       = document.querySelector('#pswd1_img1');
	var userPwdCheck = document.querySelector('#userPwdCheck');
	
	var pwImg2       = document.querySelector('#pswd2_img1');
	var pwMsgArea    = document.querySelector('.int_pass');
	var userName     = document.querySelector('#userName');
	var gender       = document.querySelector('#gender');
	var userEmail    = document.querySelector('#userEmail');
	
	var userTelNum   = document.querySelector('#userTelNum');
	
	var error        = document.querySelectorAll('.error_next_box');
	
	/*이벤트 핸들러 연결*/
	userId.addEventListener("focusout",       checkId);
	userPwd.addEventListener("focusout",      checkPw);
	userPwdCheck.addEventListener("focusout", comparePw);
	userName.addEventListener("focusout",     checkName);
	
	userEmail.addEventListener("focusout",  isEmailCorrect);
	userTelNum.addEventListener("focusout", checkPhoneNum);

	/*콜백 함수*/
	function checkId() {
		
	    var idPattern = /[a-zA-Z0-9_-]{5,20}/;
	    
	    if (userId.value === "") {
	        error[0].innerHTML     = "필수 정보입니다.";
	        error[0].style.display = "block";
	        
	    } else if (!idPattern.test(userId.value)) {
	        error[0].innerHTML     = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
	        error[0].style.display = "block";
	        
	    } else {
			console.log('Admin Id : ' + userId.value);
			$.ajax({
				url: '/camping/admin/id_check?admin_id=' + userId.value,
				type: 'POST',
				success: function(result) {
					if (result.intRetVal == 0) {
						error[0].innerHTML     = "사용 가능한 아이디 입니다.";
	        			error[0].style.color   = "#08A600";
	        			error[0].style.display = "block";
					} else {
						error[0].innerHTML     = "중복된 아이디 입니다.";
	        			error[0].style.display = "block";
	        			
	        			userId.value="";
					}
				},
				error: function() {
					
				}
			});
	        
	    }
	}
	
	function checkPw() {
		
	    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
	    
	    if (userPwd.value === "") {
	        error[1].innerHTML     = "필수 정보입니다.";
	        error[1].style.display = "block";
	        
	    } else if (!pwPattern.test(userPwd.value)) {
	        error[1].innerHTML           = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
	        pwMsg.innerHTML              = "사용불가";
	        pwMsgArea.style.paddingRight = "93px";
	        error[1].style.display       = "block";
	        
	        pwMsg.style.display = "block";
	        pwImg1.src = "/camping/images/registImg/m_icon_not_use.png";
	        
	    } else {
	        error[1].style.display = "none";
	        pwMsg.innerHTML        = "안전";
	        pwMsg.style.display    = "block";
	        pwMsg.style.color      = "#03c75a";
	        pwImg1.src             = "/camping/images/registImg/m_icon_safe.png";
	    }
	}
	
	function comparePw() {
		
	    if (userPwdCheck.value === userPwd.value && userPwdCheck.value != "") {
	        pwImg2.src             = "/camping/images/registImg/m_icon_check_enable.png";
	        error[2].style.display = "none";
	        
	    } else if(userPwdCheck.value !== userPwd.value) {
	        pwImg2.src             = "/camping/images/registImg/m_icon_check_disable.png";
	        error[2].innerHTML     = "비밀번호가 일치하지 않습니다.";
	        error[2].style.display = "block";
	    } 
	
	    if (userPwdCheck.value === "") {
	        error[2].innerHTML     = "필수 정보입니다.";
	        error[2].style.display = "block";
	    }
	}
	
	function checkName() {
		
	    var namePattern = /[a-zA-Z가-힣]/;
	    
	    if (userName.value === "") {
	        error[3].innerHTML     = "필수 정보입니다.";
	        error[3].style.display = "block";
	        
	    } else if(!namePattern.test(userName.value) || userName.value.indexOf(" ") > -1) {
	        error[3].innerHTML     = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)";
	        error[3].style.display = "block";
	        
	    } else {
	        error[3].style.display = "none";
	    }
	}
	
	function isEmailCorrect() {
		
	    var emailPattern = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
	
	    if (userEmail.value === ""){
			error[4].innerHTML     = "필수 정보입니다.";
	        error[4].style.display = "block"; 
	        
	    } else if (!emailPattern.test(userEmail.value) || userEmail.value.indexOf(" ") > - 1) {
			error[4].innerHTML = "이메일 형식에 맞게 작성 해주세요.";
			error[4].style.display = "block";
		} else if(!emailPattern.test(userEmail.value)) {
	        error[4].style.display = "block";
	        
	    } else {
	        error[4].style.display = "none"; 
	    }
	}
	
	function checkPhoneNum() {
		
	    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;
	
	    if (userTelNum.value === "") {
	        error[5].innerHTML     = "필수 정보입니다.";
	        error[5].style.display = "block";
	        
	    } else if(!isPhoneNum.test(userTelNum.value)) {
	        error[5].innerHTML     = "형식에 맞지 않는 번호입니다.";
	        error[5].style.display = "block";
	        
	    } else {
	        error[5].style.display = "none";
	    }
	}
	
	//주소검색
	function addrsearch() {
	    new daum.Postcode({
	        oncomplete: function (data) {
				
	            console.log(data);
	            
	            var roadAddr  = data.roadAddress;  //도로명 주소 변수
	            var jibunAddr = data.jibunAddress; //지번 주소 변수
	            
	            document.getElementById('zipCode').value = data.zonecode;
	            
	            if (roadAddr !== '') {
	                document.getElementById('baseAddr').value = roadAddr;
	            } else if (jibunAddr !== '') {
	                document.getElementById('baseAddr').value = jibunAddr;
	            }
	        }
	    }).open();
	}

	// 관리자 계정생성
	function createAdminAccount() {
		
		if (confirm("해당 정보로 관리작 계정을 생성 하시겠습니까?") == true) {
			$.ajax({
				url: '/camping/admin/create_account',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					userId    : userId.value,
					userPwd   : userPwd.value,
					userName  : userName.value,
					userEmail : userEmail.value,
					userTelNum: userTelNum.value
				}),
				success: function(result) {
					if (result.intRetVal == 0) {
						alert('관리자 계정이 생성 되었습니다.');
						location.href = '/camping/admin/admin_manage';
					} else {
						alert(result.strRetVal);
					}
				},
				error: function() {
					alert('Server Error');
				}
			});
		} else {
			return;
		}
	}