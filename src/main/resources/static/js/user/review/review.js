	/* 후기 작성 */
	function reviewRegist(campNo) {
	    
		let formData = new FormData();
	    
		let reviewTitle = $("#reviewTitle").val();
		let reviewDetail = $("#reviewDetail").val();
		let image = $("#file")[0].files[0];
		
		// 제목, 내용 작성하지 않으면 리턴
	    if (reviewTitle.trim() === '') {
	        alert('제목을 작성해주세요');
	        return;
	    }
	    if (reviewDetail.trim() === '') {
	        alert('내용을 작성해주세요');
	        return;
	    }
		
		formData.append('reviewTitle',  reviewTitle);
		formData.append('reviewDetail', reviewDetail);
		formData.append('campNo',       campNo);
		
		if (image) {
			formData.append('file', image);
		}
		
		$.ajax({
			url: '/camping/user/review_write',
			type: 'POST',
			processData: false,
			contentType: false, 
			data: formData,
			success: function (result) {
				console.log(result);
				if (result.intRetVal == 0) {
					alert("후기글이 등록 되었습니다.");
				    window.location.href = '/camping/user/review_detail?review_no=' + result.intReviewNo + '&camp_no=' + result.intCampNo + '&page_info=campPage';
				} else {
					alert(result.strRetVal);
				}
			},
		})
	}
	
	// 후기 삭제 (캠프번호 수정)
	function reviewDelete(reviewNo, campNo, pageInfo) {
		
		if (confirm('글을 삭제 하시겠습니까?')) {
			$.ajax({
				url: '/camping/user/review_delete',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
	                                  reviewNo : reviewNo,
	                                  pageInfo : pageInfo
	                                 }),
				success: function (result) {
					if (result.intRetVal == 0) {
						alert('글이 삭제 되었습니다.');
						if (result.strPageInfo == 'campPage') {
						    window.location.href = '/camping/user/camp_detail_review?camp_no=' + campNo;
						} else if (result.strPageInfo == 'myPage') {
	                        window.location.href = '/camping/user/my_review';
	                    }
					} else {
						alert(result.strRetVal);
					}
				},
				error: function () {
					alert('ERROR');
				}
			});
		}
	}
	
	$(document).ready(function() {
	    
	    if (typeof reviewImgPath !== 'undefined' && reviewImgPath != null) {
	        $('#imgDelete').css('display', 'block');
	    }
	});
	
	// 후기 수정
	function reviewUpdate(reviewNo, campNo, pageInfo) {
		
		let formData = new FormData();
		
		let reviewTitle  = $("#reviewTitle").val();
		let reviewDetail = $("#reviewDetail").val();
		let image = $("#file")[0].files[0];
		
		// 제목, 내용 작성하지 않으면 리턴
	    if (reviewTitle.trim() === '') {
	        alert('제목을 작성해주세요');
	        return;
	    }
	    if (reviewDetail.trim() === '') {
	        alert('내용을 작성해주세요');
	        return;
	    }
	    
		formData.append('pageInfo',     pageInfo);
		formData.append('reviewNo',     reviewNo);
		formData.append('campNo',       campNo);
		formData.append('reviewTitle',  reviewTitle);
		formData.append('reviewDetail', reviewDetail);
	    formData.append('imgState',     imageState);
	    
	    if (image) {
	        formData.append('file', image);
	    }
		
		$.ajax({
			url: '/camping/user/review_update',
			type: 'POST',
			processData: false,
			contentType: false, 
			data: formData,
			success: function (result) {
				console.log(result);
				if (result.intRetVal == 0) {
					alert("수정 완료되었습니다.");
				    window.location.href = '/camping/user/review_detail?review_no=' + result.intReviewNo + '&camp_no=' + result.intCampNo + "&page_info=" + result.strPageInfo;
				} else {
					alert(result.strRetVal + "에러");
				}
			},
		})
	}
	
	// 1: 이미지 건들X, 2: 변경, 3: 삭제
	let imageState = 1;
	
	function setThumbnail() {
	    
	    let file = $('#file')[0].files[0];
	        
	    if (file != null) {
		   imageState = 2;
		   $('#imgDelete').css('display','block');
	    } else {
			$('#file').val(''); 
	        $('#thumbnail').attr('src', '');
	        $('#imgDelete').css('display','none');
		   imageState = 3;
	    }
	   
	    let imgCon = $('#thumbnail');
	    
	    if (file != null && !file.type.startsWith('image/')) {
	        alert('이미지만 업로드 가능합니다');
	        $('#file').val('');
	        //imgCon.empty();
	        return;
	    }
	    
	    let reader = new FileReader();
	        
	    // 파일을 읽었을 때(onload) 실행될 이벤트 핸들러 정의
	    reader.onload = function(e) {
	        imgCon.attr('src', e.target.result);
	    }
	    // 파일 내용을 DataURL 로 읽는다
	    if (file != null) reader.readAsDataURL(file);
	    
	}
	
	// 이미지 삭제
	function imgDelete() {
	    $('#file').val(''); 
	    $('#thumbnail').attr('src', '');
	    $('#imgDelete').css('display','none');
	    imageState = 3; 
	} 
	
	// 댓글 작성
	function registComment(reviewNo) {
		
		const comments = $('#comment').val();
		console.log('Comments : ' + comments);
		
	    if (!comments) {
	   		 alert('댓글 내용을 입력해 주세요.');
	   	   return;
	    }
	    
		if (confirm("댓글을 작성하시겠습니까?") == true) {
			
			$.ajax({
				url: '/camping/user/comment_regist',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					reviewNo: reviewNo,
					comments: comments
				}),
				success: function (result) {
					if (result.intRetVal == 0) {
						alert('댓글이 등록 되었습니다.');
						location.reload();
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
	
	
	// 수정버튼 눌렀을 떄
	function updateReady(thisButton) {
		$(thisButton).closest('.comment-actions').siblings('.my-comment').css('display', 'none');
		$(thisButton).css('display', 'none');
		$(thisButton).closest('.comment-actions').siblings('.new-comment').css('display', 'inline-block');
		$(thisButton).siblings('.update-ok').css('display', 'inline-block');
		$(thisButton).siblings('.update-cancel').css('display', 'inline-block');
		$(thisButton).siblings('.delete-commnet').css('display', 'none');
	}
	
	//  수정 취소 눌렀을 떄
	function updateCancel(thisButton) {
		$(thisButton).closest('.comment-actions').siblings('.my-comment').css('display', 'inline-block');
		$(thisButton).siblings('.update-ready-botton').css('display', 'inline-block');
		$(thisButton).closest('.comment-actions').siblings('.new-comment').css('display', 'none');
		$(thisButton).siblings('.update-ok').css('display', 'none');
		$(thisButton).css('display', 'none');
		$(thisButton).siblings('.delete-commnet').css('display', 'inline-block');
	}
	
	// 수정 확인 눌렀을 때
	function updateOk(thisButton,reviewCommentNo) {
		
		const newComment = $(thisButton).closest('.comment-actions').siblings('.new-comment').val();
		
		if (confirm("댓글을 수정하시겠습니까?") == true) {
			
			$.ajax({
				url: '/camping/user/comment_update',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify({
					reviewCommentNo: reviewCommentNo,
					comments: newComment
				}),
				success: function (result) {
					if (result.intRetVal == 0) {
						alert('댓글이 수정 되었습니다.');
						location.reload();
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
	
	
	
	function deleteComment(reviewCommentNo) {
		
		if (confirm("댓글을 삭제하시겠습니까?") == true) {
			
			$.ajax({
				url: '/camping/user/comment_delete',
				type: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(reviewCommentNo),
				success: function (result) {
					if (result.intRetVal == 0) {
						alert('댓글이 삭제 되었습니다.');
						location.reload();
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


	function movePage(movePageNum, campNo) {
		
	    
	    let requestUrl = '/camping/user/camp_detail_review';
	    
	    let objQueryParam = {};
	
	    // searchOption 은 campList.html 에서 초기화
	    objQueryParam.page    = movePageNum;
	    objQueryParam.camp_no = campNo;
	    objQueryParam.limit   = 10;
	    
	    window.location.href  = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
	}