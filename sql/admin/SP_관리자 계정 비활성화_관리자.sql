--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_ACCOUNT_STOP
-- Description       : 관리자 계정 비활성화
-- Author            : KSW, 2024-11-20
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_ACCOUNT_STOP(
    pi_strStopId      IN  VARCHAR2,
    pi_strAdminId     IN  VARCHAR2,
    pi_strReason      IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   (
            SELECT StateCode
            FROM   TUserHis
            WHERE  UserIdOrEmail = pi_strStopId
            ORDER BY UpdDate DESC
            )
    WHERE  ROWNUM = 1;

    -- 유효성 검사
    IF v_intStateCode = 900 THEN
        po_intRetVal := 1310;
        po_strRetVal := '이미 비활성화 중인 계정입니다.';
        RETURN;
    END IF;

        INSERT INTO TUserHis(
                             UserHisNo, UserIdOrEmail, UpdDate, AdminId, StateCode
                            ,Reason
                             )
                      VALUES(
                             UserHisNo_SEQ.NEXTVAL, pi_strStopId, SYSDATE, pi_strAdminId, 900
                            ,pi_strReason
                             );
        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_ACCOUNT_STOP;