--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_ACCOUNT_DUP
-- Description       : 관리자 아이디 중복확인
-- Author            : KSW, 2024-11-20
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_ACCOUNT_DUP
(
    pi_strAdminId IN  VARCHAR2,
    
    po_intRetVal  OUT NUMBER,
    po_strRetVal  OUT VARCHAR2
) AS
BEGIN
    DECLARE
        v_intCount NUMBER;
    BEGIN
        SELECT COUNT(*)
        INTO   v_intCount
        FROM   TUserInfo
        WHERE  UserId = pi_strAdminId;

        IF v_intCount > 0 THEN
            po_intRetVal := 1110;
            po_strRetVal := '중복된 아이디 입니다.';
            RETURN;
        ELSIF v_intCount = 0 THEN
            po_intRetVal := 0;
            po_strRetVal := '사용 가능';
            RETURN;
        END IF;
    END;
END ADMIN_ACCOUNT_DUP;