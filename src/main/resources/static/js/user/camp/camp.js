
// 추천 or 추천취소 실행
function suggestionFunction(campNo, suggestionCheck) {
    
    if (suggestionCheck) {
        suggestionDelete(campNo);
        
    } else {
        suggestion(campNo);
    }
}
// 캠핑장 추천
function suggestion(campNo) {
    
    $.ajax({
        url: "/camping/user/suggestion",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({campNo: campNo}),
        success: function(result) {
            if (result.intRetVal == 0) {
                location.reload();
                alert('추천되었습니다');
            } else {
                alert(result.strRetVal);
            }
        },
        error: function() {
            alert('Server Error');
        }
    });
}

// 캠핑장 추천 취소
function suggestionDelete(campNo) {
    $.ajax({
        url: "/camping/user/suggestion_delete",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({campNo: campNo}),
        success: function(result) {
            if (result.intRetVal == 0) {
                location.reload();
                alert('추천취소되었습니다');
            } else {
                alert(result.strRetVal);
            }
        },
        error: function() {
            alert('Server Error');
        }
    });
}

function cartFunction(campNo, cartCheck) {
    if (cartCheck) {
        cartDelet(campNo);
    } else {
        cartIn(campNo);
    }
}

// 캠핑장 찜
function cartIn(campNo) {
    $.ajax({
        url: "/camping/user/cartin",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({campNo: campNo}),
        success: function(result) {
            if (result.intRetVal == 0) {
                location.reload();
                alert('해당 캠핑장이 찜 목록에 추가되었습니다');
            } else {
                alert(result.strRetVal);
            }
        },
        error: function() {
            alert('Server Error');
        }
    });
}

// 캠핑장 찜 삭제
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

function startDateCheck() {
    
    let startDate = $('#startDate');
    
    let today = new Date();
    
    let year  = today.getFullYear();
    let month = (today.getMonth() + 1).toString().padStart(2, '0');
    let day   = today.getDate().toString().padStart(2, '0');
    
    let strToday = year + '-' + month + '-' + day;
    
    if (startDate.val()) { // 값이 입력되었는지 확인
        if (startDate.val() < strToday) {
            alert("오늘 이후 날짜만 선택할 수 있습니다");
            startDate.val('');
        }
    }
    
    let startReset = true;
    let endReset   = false;
    
    // 시작일 < 종료일 인지 검사
    dateCompare(startReset, endReset);
}

function endDateCheck() {
    
    let endDate = $('#endDate');
    
    let today = new Date();
    
    let year  = today.getFullYear();
    let month = (today.getMonth() + 1).toString().padStart(2, '0');
    let day   = today.getDate().toString().padStart(2, '0');
    
    let strToday = year + '-' + month + '-' + day;
    
    if (endDate.val()) { // 값이 입력되었는지 확인
        if (endDate.val() < strToday) {
            alert("오늘 이후 날짜만 선택할 수 있습니다");
            endDate.val('');
        }
    }
    
    let startReset = false;
    let endReset   = true;
    
    // 시작일 < 종료일 인지 검사
    dateCompare(startReset, endReset);
}

// 시작일 < 종료일 인지 검사
function dateCompare(startReset, endReset) {
    
    let startDate = $('#startDate');
    let endDate = $('#endDate');
    
    if (startDate.val() != '' && endDate.val() != '') {
        
        if (startDate.val() >= endDate.val()) {
            alert('2일 이상 선택해야 합니다');
            
            if (startReset) startDate.val('');
            if (endReset) endDate.val('');
        }
    }
}

// 캠핑장 예약
function reservation(intCampNo) {
    
    let startDate = $('#startDate').val();
    let endDate     = $('#endDate').val();
    let peopleCount = $('#peopleCount').val();
    
    if (startDate == '') {
        alert('시작일을 입력해주세요');
        return;
    }
    if (endDate == '') {
        alert('종료일을 입력해주세요');
        return;
    }
    if (peopleCount == '') {
        alert('인원수를 선택해주세요');
        return;
    }
    
    if (!confirm('예약 신청하시겠습니까?')){
        return;
    }
    
    $.ajax({
        url: "/camping/user/reservation_regist",
        method: "POST",
        contentType: 'application/json',
        data: JSON.stringify({
                              campNo:      intCampNo,
                              startDate:   startDate,
                              endDate:     endDate,
                              peopleCount: peopleCount
                             }),
        success: function(result) {
            if (result.intRetVal == 0) {
                location.reload();
                alert('예약되었습니다');
            } else {
                alert(result.strRetVal);
            }
        },
        error: function() {
            alert('Server Error');
        }
    });
}

// 캠핑장 검색 URL 생성, GET 요청
function search() {
    
    let requestUrl = '/camping/user/camp_list';
    
    let objQueryParam = {};

    //objQueryParam.page = 1;
    if ($('#limit').val() != '')                           objQueryParam.limit                         = $('#limit').val();
    if ($('#searchKey').val() != '')                       objQueryParam.search_key                    = $('#searchKey').val().trim();
    if ($('#provinceName').val() != '')                    objQueryParam.province_name                 = $('#provinceName').val();
    if ($('#businessEntity').val() != '')                  objQueryParam.business_entity               = $('#businessEntity').val();
    if ($('#campingType').val() != '')                     objQueryParam.camping_type                  = $('#campingType').val();
    
    if ($('#electricitySubfacility').prop('checked'))      objQueryParam.electricity_subfacility       = $('#electricitySubfacility').prop('checked');
    if ($('#hotWaterSubfacility').prop('checked'))         objQueryParam.hotWater_subfacility          = $('#hotWaterSubfacility').prop('checked');
    if ($('#wirelessInternetSubfacility').prop('checked')) objQueryParam.wireless_internet_subfacility = $('#wirelessInternetSubfacility').prop('checked');
    if ($('#firewoodSalesSubfacility').prop('checked'))    objQueryParam.firewood_sales_subfacility    = $('#firewoodSalesSubfacility').prop('checked');
    if ($('#walkingTrailSubfacility').prop('checked'))     objQueryParam.walking_trail_subfacility     = $('#walkingTrailSubfacility').prop('checked');
    
    if ($('#waterPlayAreaSubfacility').prop('checked'))    objQueryParam.water_play_area_subfacility   = $('#waterPlayAreaSubfacility').prop('checked');
    if ($('#playgroundSubfacility').prop('checked'))       objQueryParam.playground_subfacility        = $('#playgroundSubfacility').prop('checked');
    if ($('#martSubfacility').prop('checked'))             objQueryParam.mart_subfacility              = $('#martSubfacility').prop('checked');
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}

function movePage(movePageNum) {
    
    let requestUrl = '/camping/user/camp_list';
    
    let objQueryParam = {};

    // searchOption 은 campList.html 에서 초기화
    objQueryParam.page = movePageNum;
    
    if (searchOption.limit != null)               objQueryParam.limit                         = searchOption.limit;
    if (searchOption.searchKey != null)           objQueryParam.search_key                    = searchOption.searchKey;
    if (searchOption.provinceName != null)        objQueryParam.province_name                 = searchOption.provinceName;
    if (searchOption.businessEntity != null)      objQueryParam.business_entity               = searchOption.businessEntity;
    if (searchOption.campingType != null)         objQueryParam.camping_type                  = searchOption.campingType;
    
    if (searchOption.electricitySubfacility)      objQueryParam.electricity_subfacility       = searchOption.electricitySubfacility;
    if (searchOption.hotWaterSubfacility)         objQueryParam.hotWater_subfacility          = searchOption.hotWaterSubfacility;
    if (searchOption.wirelessInternetSubfacility) objQueryParam.wireless_internet_subfacility = searchOption.wirelessInternetSubfacility;
    if (searchOption.firewoodSalesSubfacility)    objQueryParam.firewood_sales_subfacility    = searchOption.firewoodSalesSubfacility;
    if (searchOption.walkingTrailSubfacility)     objQueryParam.walking_trail_subfacility     = searchOption.walkingTrailSubfacility;
    
    if (searchOption.waterPlayAreaSubfacility)    objQueryParam.water_play_area_subfacility   = searchOption.waterPlayAreaSubfacility;
    if (searchOption.playgroundSubfacility)       objQueryParam.playground_subfacility        = searchOption.playgroundSubfacility;
    if (searchOption.martSubfacility)             objQueryParam.mart_subfacility              = searchOption.martSubfacility;
    
    window.location.href = requestUrl + '?' + new URLSearchParams(objQueryParam).toString();
}

function toggleOption() {
    

    if ($('#searchOptionContainer').css('display') == 'block') { // 상세옵션 닫기
        
        $('#searchOptionContainer').css('display', 'none');
        $('#optionShowText').text('상세옵션 열기');
        
        $('#bannerContainer').css('height', '350px');
        
    } else { // 상세옵션 열기
        
        $('#searchOptionContainer').css('display', 'block');
        $('#optionShowText').text('상세옵션 닫기');
        
        $('#bannerContainer').css('height', '485px');
        
    }
}







