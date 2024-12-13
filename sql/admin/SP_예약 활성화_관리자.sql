--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_RESERVATION_ACT
-- Description       : 예약 보류
-- Author            : KSW, 2024-12-03
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_RESERVATION_ACT (
    pi_strReservationNo IN  VARCHAR2,
    pi_strAdminId       IN  VARCHAR2,
    
    po_intRetVal        OUT NUMBER,
    po_strRetVal        OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   TCampReservation
    WHERE  ReservationNo = pi_strReservationNo;

    IF v_intStateCode = 100 THEN
        po_intRetVal := 1310;
        po_strRetVal := '이미 활성화중인 예약정보 입니다.';
        RETURN;
    END IF;

        UPDATE TCampReservation
        SET    StateCode = 100
        WHERE  ReservationNo = pi_strReservationNo;

        INSERT INTO TCampReservationHis(
                                        ReservationHisNo, ReservationNo, UpdDate, AdminId, StateCode
                                        )
                                 VALUES(
                                        ReservationHisNo_SEQ.NEXTVAL, pi_strReservationNo, SYSDATE, pi_strAdminId, 100
                                        );

        po_intRetVal := 0;
        po_strRetVal := '해당 예약정보의 보류가 취소 되었습니다.';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_RESERVATION_ACT;