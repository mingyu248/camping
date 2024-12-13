--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_CAMPSTATUS_STOP
-- Description       : 캠핑장 비활성화
-- Author            : KSW, 2024-11-21
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_CAMPSTATUS_STOP(
    pi_intCampNo      IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   TCampInfo
    WHERE  CampNo = pi_intCampNo;

    -- 유효성 검사
    IF v_intStateCode = 900 THEN
        po_intRetVal := 2110;
        po_strRetVal := '이미 비활성화 중인 캠핑장 정보 입니다.';
        RETURN;
    END IF;

        UPDATE TCampInfo
        SET    StateCode = 900
              ,UpdDate   = SYSDATE
        WHERE  CampNo = pi_intCampNo;

        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_CAMPSTATUS_STOP;