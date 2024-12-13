    let checkInDate  = null;
    let checkOutDate = null;
    
    let strCheckInDate      = '';
    let strCheckOutDate     = '';
    let strPeopleCount      = '';
    let strCampRoomNo       = '';
    
    function setReservationInfoText() {
        
        $('#reservation-info-text').text('');
        
        if (checkInDate && checkOutDate && strPeopleCount && strCampRoomNo) {
           $('#reservation-info-text1').text(strCheckInDate + ' 16시 부터 ' + strCheckOutDate + ' 11시 까지');
           $('#reservation-info-text2').text(strCampRoomNo + '번방 ' + strPeopleCount + '명');
        }
    }
    
    // 달력 범위체크
    document.addEventListener('DOMContentLoaded', function() {
        flatpickr("#date-picker", {
            
            mode: "range", // Range mode for selecting a range
            dateFormat: "Y년 m월 d일",
            onChange: function(selectedDates) {
                
                // 방 선택 초기화
                strCampRoomNo = '';
                $('.room').each(function(index, element) {
                    $(element).css('background-color', '#041625');
                });
                
                // Update display for check-in and check-out dates
                if (selectedDates.length > 0) {
                    checkInDate = selectedDates[0];
                    strCheckInDate = selectedDates[0].toISOString().split("T")[0];
                }
                if (selectedDates.length > 1) {
                    checkOutDate = selectedDates[1];
                    strCheckOutDate = selectedDates[1].toISOString().split("T")[0];
                }
                setReservationInfoText();
            }
        });
    });
    
    function setPeopleCount() {
        
        strPeopleCount = $('#peopleCount').val();
        setReservationInfoText();
    }
    
    function setCampRoomSelect(campRoomNo) {
        
        strCampRoomNo = campRoomNo;
        
        $('.room').each(function(index, element) {
            $(element).css('background-color', '#041625');
        });
        
        $('.room').eq(campRoomNo-1).css({
            'background-color': '#1262a3',
        });
        
        setReservationInfoText();
    }
    
    function confirmReservation() {
        
        if (!checkInDate || !checkOutDate) {
           alert('날짜를 선택해주세요');
           return;
        }
        
        if (!strPeopleCount) {
            alert('인원수를 선택해주세요');
            return;
        }
        if (!strCampRoomNo) {
            alert('방을 선택해주세요');
            return;
        }
        
        if (confirm('해당 정보로 예약 하시겠습니까?') == true) {
            $.ajax({
                url: '/camping/user/reserve_camp',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify({
                    campNo      : $('#campNo').val(),
                    campRoomNo  : strCampRoomNo,
                    peopleCount : strPeopleCount,
                    checkInDate : checkInDate,
                    checkOutDate: checkOutDate
                }),
                success: function(result) {
                    if (result.intRetVal == 0) {
                        alert(result.strRetVal);
                        window.location.href = '/camping/user/my_reservation'
                    } else {
                        alert(result.strRetVal);
                    }
                }
            });
        } else {
            return;
        }
    }
    
    function dateConfirm() {
        if (checkOutDate == null) return;
        $.ajax({
            url: '/camping/user/get_reservation_available_room_list',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                campNo:       $('#campNo').val(),
                checkInDate:  checkInDate,
                checkOutDate: checkOutDate
            }),
            success: function(objReservationList) {
                
                objReservationList.forEach((reservation, i) => {
                    
                    if (reservation.reservationCount != 0) {
                        $('.room').eq(i).text('예약된 방입니다').prop('disabled', true);
                    } else {
                        $('.room').eq(i).text(i + 1 + '번방').prop('disabled', false);
                    }
                });
            },
            error:function() {
                alert('Server Error');
            }
        });
    }

    
	