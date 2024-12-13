--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_SOCIAL_DEL
-- Description       : 후기 삭제(관리자)
-- Author            : KSW, 2024-11-22
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_SOCIAL_DEL
(
    pi_strDelUserEmail IN  VARCHAR2,
    
    po_intRetVal       OUT NUMBER,
    po_strRetVal       OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   User_Tbl
    WHERE  Email = pi_strDelUserEmail;

    IF v_intStateCode != 999 THEN
        po_intRetVal := 1410;
        po_strRetVal := '탈퇴신청된 계정이 아닙니다.';
        RETURN;
    END IF;

        DELETE 
        FROM   User_Tbl    
        WHERE  Email = pi_strDelUserEmail;

        DELETE 
        FROM   TUserHis
        WHERE  UserIdOrEmail = pi_strDelUserEmail;

        po_intRetVal := 0;
        po_strRetVal := '해당 계정이 제거 되었습니다.';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM(SQLCODE);
END ADMIN_SOCIAL_DEL;