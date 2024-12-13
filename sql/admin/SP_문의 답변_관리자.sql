--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_HELPANSWER_INS
-- Description       : 관리자 문의 답변
-- Author            : 강승원, 2024-11-21
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_HELPANSWER_INS(
    pi_intHelpNo      IN  VARCHAR2,
    pi_strAnswer      IN  VARCHAR2,
    pi_strAdminId     IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   THelp
    WHERE  HelpNo = pi_intHelpNo;

    -- 유효성 검사
    IF v_intStateCode = 100 THEN
        po_intRetVal := 6110;
        po_strRetVal := '이미 답변 완료 된 문의 글 입니다.';
        RETURN;
    END IF;

        UPDATE THelp
        SET    AdminId    = pi_strAdminId
              ,Answer     = pi_strAnswer
              ,AnswerDate = SYSDATE
              ,StateCode  = 100
        WHERE  HelpNo = pi_intHelpNo;

        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_HELPANSWER_INS;