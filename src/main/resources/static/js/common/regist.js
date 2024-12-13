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
	var userBirth    = document.querySelector('#userBirth');
	var gender       = document.querySelector('#gender');
	
	var userEmail    = document.querySelector('#userEmail');
	var userTelNum   = document.querySelector('#userTelNum');
	var error        = document.querySelectorAll('.error_next_box');
	
	
	let idOk       = false;
    let pwdOk      = false;
    let pwdCheckOk = false;
    let nameOk     = false;
    let birthOk    = false;
    
    let genderOk   = false;
    let emailOk    = true;
    let telOk      = false;
	
	/*이벤트 핸들러 연결*/
	if (userId) userId.addEventListener("focusout",             checkId);
	if (userPwd) userPwd.addEventListener("focusout",           checkPw);
	if (userPwdCheck) userPwdCheck.addEventListener("focusout", comparePw);
	if (userName) userName.addEventListener("focusout",         checkName);
	if (userBirth) userBirth.addEventListener("focusout",       isBirthCompleted);
	
	if (gender) {
    	gender.addEventListener("focusout", function() {
            
            let genderIndexNum = 5;
            if (error.length == 1) genderIndexNum = 0;
    		
    	    if (gender.value === "성별") {
    	        error[genderIndexNum].style.display = "block";
    	        genderOk = false;
    	    } else {
    	        error[genderIndexNum].style.display = "none";
    	        genderOk = true;
    	    }
    	})
	}
	if (userEmail) userEmail.addEventListener("focusout",   isEmailCorrect);
	if (userTelNum) userTelNum.addEventListener("focusout", checkPhoneNum);

	/*콜백 함수*/
	function checkId() {
        
        let userIdIndexNum = 0;
        //if (error.length == 1) userIdIndexNum = 0;
		
	    var idPattern = /[a-zA-Z0-9_-]{5,20}/;
	    
	    if (userId.value === "") {
	        error[userIdIndexNum].innerHTML     = "필수 정보입니다.";
	        error[userIdIndexNum].style.display = "block";
	        idOk = false;
	        
	    } else if (!idPattern.test(userId.value)) {
	        error[userIdIndexNum].innerHTML     = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
	        error[userIdIndexNum].style.display = "block";
	        idOk = false;
	        
	    } else {
            
            $.ajax({
                url:'/camping/user/user_duplicate_check',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(userId.value),
                success: function (result) {
                    
                    if (result.intRetVal == 0) {
                        error[userIdIndexNum].innerHTML     = "사용 가능한 아이디";
                        error[userIdIndexNum].style.color   = "#08A600";
                        error[userIdIndexNum].style.display = "block";
                        idOk = true;
                        
                    } else if (result.intRetVal == 1100) {
                        error[userIdIndexNum].innerHTML     = "중복된 아이디";
                        error[userIdIndexNum].style.color   = "red";
                        error[userIdIndexNum].style.display = "block";
                        idOk = false;
                    } else {
                        
                    }
                },
                error: function () {
                    alert('ERROR');
                    idOk = false;
                }   
            });
	    }
	}
	
	function checkPw() {
        
        let userPwdIndexNum = 1;
        if (error.length == 2) userPwdIndexNum = 0;
		
	    var pwPattern = /[a-zA-Z0-9~!@#$%^&*()_+|<>?:{}]{8,16}/;
	    
	    if (userPwd.value === "") {
	        error[userPwdIndexNum].innerHTML     = "필수 정보입니다.";
	        error[userPwdIndexNum].style.display = "block";
	        pwdOk = false;
	    } else if (!pwPattern.test(userPwd.value)) {
	        error[userPwdIndexNum].innerHTML           = "8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.";
	        if (pwMsg) pwMsg.innerHTML              = "사용불가";
	        if (pwMsgArea) pwMsgArea.style.paddingRight = "93px";
	        error[userPwdIndexNum].style.display       = "block";
	        
	        if (pwMsg) pwMsg.style.display = "block";
	        if (pwImg1) pwImg1.src = "/camping/images/registImg/m_icon_not_use.png";
	        pwdOk = false;
	        
	    } else {
	        error[userPwdIndexNum].style.display = "none";
	        if (pwMsg) pwMsg.innerHTML        = "안전";
	        if (pwMsg) pwMsg.style.display    = "block";
	        if (pwMsg) pwMsg.style.color      = "#03c75a";
	        if (pwImg1) pwImg1.src             = "/camping/images/registImg/m_icon_safe.png";
	        comparePw();
	        pwdOk = true;
	    }
	}
	
	function comparePw() {
        
        let userPwdCheckIndexNum = 2;
        if (error.length == 2) userPwdCheckIndexNum = 1;
		
	    if (userPwdCheck.value === userPwd.value && userPwdCheck.value != "") {
	        if (pwImg2) pwImg2.src             = "/camping/images/registImg/m_icon_check_enable.png";
	        error[userPwdCheckIndexNum].innerHTML     = "비밀번호가 일치합니다";
	        error[userPwdCheckIndexNum].style.display = "block";
	        error[userPwdCheckIndexNum].style.color = "green";
	        pwdCheckOk = true;
	        
	    } else if(userPwdCheck.value !== userPwd.value) {
	        if (pwImg2) pwImg2.src             = "/camping/images/registImg/m_icon_check_disable.png";
	        error[userPwdCheckIndexNum].innerHTML     = "비밀번호가 일치하지 않습니다.";
	        error[userPwdCheckIndexNum].style.display = "block";
	        error[userPwdCheckIndexNum].style.color = "red";
	        pwdCheckOk = false;
	    } 
	
	    if (userPwdCheck.value === "") {
	        error[userPwdCheckIndexNum].innerHTML     = "필수 정보입니다.";
	        error[userPwdCheckIndexNum].style.display = "block";
	        pwdCheckOk = false;
	    }
	}
	
	function checkName() {
        
        let userNameCheckIndexNum = 3;
        if (error.length == 1) userNameCheckIndexNum = 0;
		
	    var namePattern = /^[a-zA-Z가-힣]+$/;
	    
	    if (userName.value === "") {
	        error[userNameCheckIndexNum].innerHTML     = "필수 정보입니다.";
	        error[userNameCheckIndexNum].style.display = "block";
	        nameOk = false;
	    } else if(!namePattern.test(userName.value) || userName.value.indexOf(" ") > -1) {
	        error[userNameCheckIndexNum].innerHTML     = "한글과 영문 대 소문자를 사용하세요. (특수기호, 공백 사용 불가)";
	        
	        error[userNameCheckIndexNum].style.display = "block";
	        nameOk = false;
	    } else {
	        error[userNameCheckIndexNum].style.display = "none";
	        nameOk = true;
	    }
	}
	
	function isBirthCompleted() {
        
        let userBirthCheckIndexNum = 4;
        if (error.length == 1) userBirthCheckIndexNum = 0;
        
        if (userBirth.value == '') {
            error[userBirthCheckIndexNum].innerHTML     = "생년월일을 입력해주세요";
            error[userBirthCheckIndexNum].style.display = "block";
            birthOk = false;
        } else {
            error[userBirthCheckIndexNum].style.display = "none";
            birthOk = true;
        }
		
	}
	
	function checkAge() {
        
        let userBirthCheckIndexNum = 4;
        if (error.length == 1) userBirthCheckIndexNum = 0;
		
		const year = Number(userBirth.split('-')[0]);
		
	    if (Number(year) < 1920) {
	        error[userBirthCheckIndexNum].innerHTML     = "정말이세요?";
	        error[userBirthCheckIndexNum].style.display = "block";
	        
	    } else if (Number(year) > 2025) {
	        error[userBirthCheckIndexNum].innerHTML     = "미래에서 오셨군요. ^^";
	        error[userBirthCheckIndexNum].style.display = "block";
	        
	    } else if (Number(year) > 2005) {
	        error[userBirthCheckIndexNum].innerHTML     = "만 14세 미만의 어린이는 보호자 동의가 필요합니다.";
	        error[userBirthCheckIndexNum].style.display = "block";
	        
	    } else {
	        error[userBirthCheckIndexNum].style.display = "none";
	    }
	}
	
	function isEmailCorrect() {
        
        let emailIndexNum = 6;
        if (error.length == 1) emailIndexNum = 0;
        
	    var emailPattern = /[a-z0-9]{2,}@[a-z0-9-]{2,}\.[a-z0-9]{2,}/;
	    
	    if (userEmail.value === ""){ 
            error[emailIndexNum].style.display="none";
	        emailOk = true;
	    } else if(!emailPattern.test(userEmail.value)) {
            error[emailIndexNum].style.display="block";
	        emailOk = false;
	    } else {
	        error[emailIndexNum].style.display = "none";
	        emailOk = true;
	    }
	}
	
	function checkPhoneNum() {
        
        let userTelNumIndexNum = 7;
        if (error.length == 1) userTelNumIndexNum = 0;
		
	    var isPhoneNum = /([01]{2})([01679]{1})([0-9]{3,4})([0-9]{4})/;
	
	    if (userTelNum.value === "") {
	        error[userTelNumIndexNum].innerHTML     = "필수 정보입니다.";
	        error[userTelNumIndexNum].style.display = "block";
	        telOk = false;
	    } else if(!isPhoneNum.test(userTelNum.value)) {
	        error[userTelNumIndexNum].innerHTML     = "형식에 맞지 않는 번호입니다.";
	        error[userTelNumIndexNum].style.display = "block";
	        telOk = false;
	    } else {
	        error[userTelNumIndexNum].style.display = "none";
	        telOk = true;
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
	
	function userRegist() {
        
        if (!idOk) {
            alert("아이디를 확인해주세요");
            userId.focus();
            return;
        }
        
        if (!pwdOk) {
            alert("패스워드를 확인해주세요");
            userPwd.focus();
            return;
        }
        
        if (!pwdCheckOk) {
            alert("패스워드 체크를 확인해주세요");
            userPwdCheck.focus();
            return;
        }
        
        if (!nameOk) {
            alert("이름을 확인해주세요");
            userName.focus();
            return;
        }
        
        if (!birthOk) {
            alert("생일을 확인해주세요");
            userBirth.focus();
            return;
        }
        
        if (!genderOk) {
            alert("성별을 확인해주세요");
            gender.focus();
            return;
        }
        
        if (!emailOk) {
            alert("이메일을 확인해주세요");
            userEmail.focus();
            return;
        }
        
        if (!telOk) {
            alert("휴대전화 번호를 확인해주세요");
            userTelNum.focus();
            return;
        }
        
        if ($('#zipCode').val() == '') {
            alert("우편번호를 확인해주세요");
            $('#zipCode').focus();
            return;
        }
        
        if ($('#baseAddr').val() == '') {
            alert("기본주소를 확인해주세요");
            $('#baseAddr').focus();
            return;
        }
        
        if ($('#dtlAddr').val() == '') {
            alert("상세주소를 확인해주세요");
            $('#dtlAddr').focus();
            return;
        }
        
        let strUserId     = userId.value;
        let strUserPwd    = userPwd.value;
        let strUserName   = userName.value;
        let strUserBirth  = userBirth.value;
        let strGender     = gender.value;
        let strUserTelNum = userTelNum.value;
        let strUserEmail  = userEmail.value;
        let strZipCode    = $('#zipCode').val();
        let strBaseAddr   = $('#baseAddr').val();
        let strDtlAddr    = $('#dtlAddr').val();
        
        if (confirm("아래의 정보로 회원가입을 진행하시겠습니까?")) {
            $.ajax({
                url: '/camping/user/regist',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    userId     : strUserId
                   ,userPwd    : strUserPwd
                   ,userName   : strUserName
                   ,userBirth  : strUserBirth
                   ,gender     : strGender
                   
                   ,userEmail  : strUserEmail
                   ,userTelNum : strUserTelNum
                   ,zipCode    : strZipCode
                   ,baseAddr   : strBaseAddr
                   ,dtlAddr    : strDtlAddr
                }),
                success: function (result) {
                    if (result.intRetVal == 0) {
                        alert('회원가입 되었습니다.');
                        window.location.href = '/camping/main';
                    } else {
                        alert('회원가입에 실패 했습니다. 원인 : ' + result.strRetVal);
                    }
                },
                error: function () {
                    alert('서버 에러');
                }
            });
        } else {
            return;
        }
    }
    
    // 회원정보 수정 (이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    function userInfoUpdate() {
        
        let strUserEmail     = null;
        let strUserPwd       = null;
        let strUserOriginPwd = null;
        let strZipCode       = null;
        let strBaseAddr      = null;
        
        let strDtlAddr       = null;
        let strUserBirth     = null;
        let strGender        = null;
        let strUserName      = null;
        let strUserTelNum    = null;
        
        
        if ($('#userOriginPwd')) {
            strUserOriginPwd = $('#userOriginPwd').val();
            if (strUserOriginPwd == '') {
                alert("기존 패스워드를 입력해주세요");
                $('#userOriginPwd').focus();
                return 
            }
        }
        
        if (userPwd) {
            strUserPwd         = userPwd.value;
            if (!pwdOk) {
                alert("패스워드를 확인해주세요");
                userPwd.focus();
                return;
            }
            if (!pwdCheckOk) {
                alert("패스워드 체크를 확인해주세요");
                userPwdCheck.focus();
                return;
            }
        }
        if (userName) {
            strUserName       = userName.value;
            if (!nameOk) {
                alert("이름을 확인해주세요");
                userName.focus();
                return;
            }
        }
        if (userBirth) {
            strUserBirth     = userBirth.value;
            if (!birthOk) {
                alert("생일을 확인해주세요");
                userBirth.focus();
                return;
            }
        }
        if (gender) {
            strGender           = gender.value;
            if (!genderOk) {
                alert("성별을 확인해주세요");
                gender.focus();
                return;
            }
        }
        if (userTelNum) {
            strUserTelNum   = userTelNum.value;
            if (!telOk) {
                alert("휴대전화 번호를 확인해주세요");
                userTelNum.focus();
                return;
            }
        }
        if (userEmail) {
            strUserEmail     = userEmail.value;
            if (!emailOk) {
                alert("이메일을 확인해주세요");
                userEmail.focus();
                return;
            }
            
            if (strUserEmail == '' && !confirm('이메일을 삭제하시겠습니까?')) {
                return;
            }
            
        }
        if ($('#zipCode')) {
            strZipCode   = $('#zipCode').val();
            if (strZipCode == '') {
                alert("우편번호를 확인해주세요");
                $('#zipCode').focus();
                return;
            }
        }
        if ($('#baseAddr')) {
            strBaseAddr = $('#baseAddr').val();
            if (strBaseAddr == '') {
                alert("기본주소를 확인해주세요");
                $('#baseAddr').focus();
                return;
            }
        }
        if ($('#dtlAddr')) {
            strDtlAddr   = $('#dtlAddr').val();
            if (strDtlAddr == '') {
                alert("상세주소를 확인해주세요");
                $('#dtlAddr').focus();
                return;
            }
        }
        
        if (confirm("입력한 정보로 수정하시겠습니까?")) {
            $.ajax({
                url: '/camping/user/user_info_update',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    userPwd    : strUserPwd
                   ,userOriginPwd: strUserOriginPwd
                   ,userName   : strUserName
                   ,userBirth  : strUserBirth
                   ,gender     : strGender
                   
                   ,userEmail  : strUserEmail
                   ,userTelNum : strUserTelNum
                   ,zipCode    : strZipCode
                   ,baseAddr   : strBaseAddr
                   ,dtlAddr    : strDtlAddr
                }),
                success: function (result) {
                    if (result.intRetVal == 0) {
                        alert('수정 되었습니다.');
                        window.location.href = "/camping/user/my_info";
                    } else {
                        alert(result.strRetVal);
                    }
                },
                error: function () {
                    alert('서버 에러');
                }
            });
        } else {
            return;
        }
    }
    
    function updateSocialAccount() {
		
		const userBirth  = $('#userBirth').val();
		const gender     = $('#gender').val();
		const userTelNum = $('#userTelNum').val();
		const zipCode    = $('#zipCode').val();
		const baseAddr   = $('#baseAddr').val();
		
		const dtlAddr    = $('#dtlAddr').val();
		
		if (confirm("입력한 정보로 수정 하시겠습니까?")) {
			$.ajax({
				url: '/camping/user/social_update',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
                    userBirth  : userBirth
                   ,gender     : gender
                   ,userTelNum : userTelNum
                   ,zipCode    : zipCode
                   ,baseAddr   : baseAddr
                   
                   ,dtlAddr    : dtlAddr 	
                }),
                success: function(result) {
					if (result.intRetVal == 0) {
						alert(result.strRetVal);
						location.href = '/camping/user/my_info';
					} else {
						alert(result.strRetVal);
					}
				},
				error: function(xhr) {
					alert('Server Error : ' + xhr);
				}
			});
		} else {
			return;
		}
	}