--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_HELP_DEL
-- Description       : 문의글 삭제(관리자)
-- Author            : KSW, 2024-11-27
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_HELP_DEL
(
    pi_intHelpNo IN  NUMBER,
    
    po_intRetVal  OUT NUMBER,
    po_strRetVal  OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   THelp
    WHERE  HelpNo = pi_intHelpNo;
    
    IF v_intStateCode = 200 THEN
        po_intRetVal := 6140;
        po_strRetVal := '답변 완료된 문의만 삭제 가능합니다.';
        RETURN;
    END IF;
    
    DELETE 
    FROM   THelp    
    WHERE  HelpNo = pi_intHelpNo;

    po_intRetVal := 0;
    po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := SQLCODE;
        po_strRetVal := SQLERRM(SQLCODE);
END ADMIN_HELP_DEL;
