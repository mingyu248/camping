--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_ACCOUNT_UPD
-- Description       : 관리자 계정 수정
-- Author            : KSW, 2024-11-22
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_ACCOUNT_UPD(
    pi_strAdminId     IN  VARCHAR2, -- 관리자 아이디
    pi_strUpdAdminId  IN  VARCHAR2, -- 수정할 관리자 아이디
    pi_strAdminPwd    IN  VARCHAR2, -- 비밀번호
    pi_strAdminName   IN  VARCHAR2, -- 이름
    pi_strAdminEmail  IN  VARCHAR2, -- 이메일
    
    pi_strAdminTelNum IN  VARCHAR2, -- 전화번호
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
BEGIN
        UPDATE TUserInfo
        SET    UserPwd    = CASE 
                                WHEN pi_strAdminPwd    IS NOT NULL 
                                    THEN pi_strAdminPwd ELSE UserPwd 
                            END,
               UserName   = CASE 
                                WHEN pi_strAdminName   IS NOT NULL 
                                    THEN pi_strAdminName ELSE UserName 
                            END,
               UserEmail  = CASE 
                                WHEN pi_strAdminEmail  IS NOT NULL 
                                    THEN pi_strAdminEmail ELSE UserEmail 
                            END,
               UserTelNum = CASE 
                                WHEN pi_strAdminTelNum IS NOT NULL 
                                    THEN pi_strAdminTelNum ELSE UserTelNum 
                            END
        WHERE  UserId = pi_strUpdAdminId;


        INSERT INTO TUserHis(
                             UserHisNo, UserIdOrEmail, UpdDate, AdminId, Reason
                             )
                      VALUES(
                             UserHisNo_SEQ.NEXTVAL, pi_strUpdAdminId, SYSDATE, pi_strAdminId, '정보 수정'
                             );
        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_ACCOUNT_UPD;