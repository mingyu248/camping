--------------------------------------------------------------------------------------------------------
-- ProcedureName     : USER_SOCIAL_UPD
-- Description       : 소셜 계정 정보 수정
-- Author            : KSW, 2024-11-30
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE "USER_RESERVATION_CAMP" (
    pi_intCampNo      IN  NUMBER,
    pi_strUserAccount IN  VARCHAR2,
    pi_intCampRoomNo  IN  NUMBER,
    pi_dtCheckInDate  IN  DATE,
    pi_dtCheckOutDate IN  DATE,
    
    pi_intPeopleCount IN  NUMBER,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intRoomCount NUMBER;

BEGIN
    SELECT COUNT(*)
    INTO v_intRoomCount
    FROM TCampReservation
    WHERE CampNo = pi_intCampNo
      AND CampRoomNo = pi_intCampRoomNo
      AND (
           TO_DATE(pi_dtCheckInDate || '16:00:00', 'YYYY/MM/DD HH24:MI:SS') < CheckOutDate
           AND  
           TO_DATE(pi_dtCheckOutDate || '11:00:00', 'YYYY/MM/DD HH24:MI:SS') > CheckInDate
           );

    IF pi_intPeopleCount > 4 THEN
        po_intRetVal := 8110;
        po_strRetVal := '최대 4인을 초과할 수 없습니다.';
        RETURN;
    END IF;

    IF v_intRoomCount != 0 THEN
        po_intRetVal := 8110;
        po_strRetVal := '이미 다른 고객님이 예약한 방입니다.';
        RETURN;
    END IF;

        INSERT INTO TCampReservation(
                                     ReservationNo, CampNo, CampRoomNo, UserIdOrEmail, CheckInDate
                                    ,CheckOutDate, RegDate, PeopleCount
                                    )
                              VALUES(
                                     ReservationNO_SEQ.NEXTVAL, pi_intCampNo, pi_intCampRoomNo, pi_strUserAccount, pi_dtCheckInDate
                                    ,pi_dtCheckOutDate, SYSDATE, pi_intPeopleCount
                                     );

        po_intRetVal := 0;
        po_strRetVal := '성공적으로 예약이 되었습니다.';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END USER_RESERVATION_CAMP;
