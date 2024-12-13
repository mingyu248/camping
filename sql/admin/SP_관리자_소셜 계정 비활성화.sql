--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_SOCIAL_STOP
-- Description       : 소셜 계정 비활성화
-- Author            : KSW, 2024-11-20
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_SOCIAL_STOP(
    pi_strStopEmail   IN  VARCHAR2,
    pi_strAdminId     IN  VARCHAR2,
    pi_strReason      IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   User_Tbl
    WHERE  Email = pi_strStopEmail;

    IF v_intStateCode = 900 THEN
        po_intRetVal := 1410;
        po_strRetVal := '이미 비활성화 중인 계정입니다.';
        RETURN;
    END IF;

        UPDATE User_Tbl
        SET    StateCode = 900
        WHERE  Email = pi_strStopEmail;

        INSERT INTO TUserHis(
                             UserHisNo, UserIdOrEmail, UpdDate, AdminId, StateCode
                            ,Reason
                             )
                      VALUES(
                             UserHisNo_SEQ.NEXTVAL, pi_strStopEmail, SYSDATE, pi_strAdminId, 900
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
END ADMIN_SOCIAL_STOP;