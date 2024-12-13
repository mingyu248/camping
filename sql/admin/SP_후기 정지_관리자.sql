--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_REVIEW_STOP
-- Description       : 후기 정지(관리자)
-- Author            : KSW, 2024-11-22
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_REVIEW_STOP(
    pi_intReviewNo    IN  NUMBER,
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
            FROM   TReviewHis
            WHERE  ReviewNo = pi_intReviewNo
            ORDER BY UpdDate DESC
            )
    WHERE  ROWNUM = 1;

    IF v_intStateCode = 900 THEN
        po_intRetVal := 1310;
        po_strRetVal := '이미 정지된 후기 입니다.';
        RETURN;
    END IF;

        INSERT INTO TReviewHis(
                               ReviewNo, UpdDate, StateCode, Reason, AdminId
                               )
                        VALUES(
                               pi_intReviewNo, SYSDATE, 900, pi_strReason, pi_strAdminId
                               );
        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_REVIEW_STOP;