--------------------------------------------------------------------------------------------------------
-- ProcedureName     : USER_SOCIAL_UPD
-- Description       : 소셜 계정 정보 수정
-- Author            : KSW, 2024-11-30
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE USER_SOCIAL_UPD(
    pi_strUserEmail   IN  VARCHAR2,
    pi_strUserBirth   IN  DATE,
    pi_strGender      IN  VARCHAR2,
    pi_strUserTelNum  IN  VARCHAR2,
    pi_strZipCode     IN  VARCHAR2,
    
    pi_strBaseAddr    IN  VARCHAR2,
    pi_strDtlAddr     IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS

BEGIN

        UPDATE User_Tbl
        SET    UserBirth  = CASE 
                                WHEN pi_strUserBirth    IS NOT NULL 
                                    THEN pi_strUserBirth ELSE UserBirth 
                            END,
               Gender     = CASE 
                                WHEN pi_strGender   IS NOT NULL 
                                    THEN pi_strGender ELSE Gender 
                            END,
               UserTelNum = CASE 
                                WHEN pi_strUserTelNum  IS NOT NULL 
                                    THEN pi_strUserTelNum ELSE UserTelNum 
                            END,
               ZipCode    = CASE 
                                WHEN pi_strZipCode IS NOT NULL 
                                    THEN pi_strZipCode ELSE ZipCode 
                            END,
               BaseAddr   = CASE 
                                WHEN pi_strBaseAddr IS NOT NULL 
                                    THEN pi_strBaseAddr ELSE BaseAddr 
                            END,
               DtlAddr    = CASE 
                                WHEN pi_strDtlAddr IS NOT NULL 
                                    THEN pi_strDtlAddr ELSE DtlAddr 
                            END
        WHERE  Email = pi_strUserEmail;

        INSERT INTO TUserHis(
                             UserHisNo, UserIdOrEmail, UpdDate, Reason
                             )
                      VALUES(
                             UserHisNo_SEQ.NEXTVAL, pi_strUserEmail, SYSDATE, '정보 수정'
                             );
        po_intRetVal := 0;
        po_strRetVal := '회원 정보가 수정 되었습니다.';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END USER_SOCIAL_UPD;