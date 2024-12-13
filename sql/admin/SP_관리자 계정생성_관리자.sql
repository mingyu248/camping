--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_ACCOUNT_INS
-- Description       : 관리자 계정 등록(관리자)
-- Author            : 강승원, 2024-11-20
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_ACCOUNT_INS(
    pi_strAdminId     IN  VARCHAR2,
    pi_strJoinAdminId IN  VARCHAR2,
    pi_strAdminPwd    IN  VARCHAR2,
    pi_strAdminName   IN  VARCHAR2,
    pi_strAdminEmail  IN  VARCHAR2,
    
    pi_strAdminTelNum IN  VARCHAR2,
    
    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intCount   NUMBER;
    v_intAdminNo NUMBER;
BEGIN
    SELECT COUNT(*)
    INTO   v_intCount
    FROM   TUserInfo
    WHERE  UserId = pi_strJoinAdminId;

    -- 유효성 검사
    IF v_intCount > 0 THEN
        po_intRetVal := 1110;
        po_strRetVal := '중복된 아이디 입니다.';
        RETURN;
    END IF;

        INSERT INTO TUserInfo(
                              UserNo, UserId, UserPwd, UserName, UserEmail
                             ,UserTelNum, RegDate
                              )
                       VALUES(
                              UserNo_SEQ.NEXTVAL, pi_strJoinAdminId, pi_strAdminPwd, pi_strAdminName, pi_strAdminEmail
                             ,pi_strAdminTelNum, SYSDATE
                              );

        SELECT UserNo
        INTO   v_intAdminNo
        FROM   TUserInfo
        WHERE  UserId = pi_strJoinAdminId;

        INSERT INTO TUserHis(
                             UserNo, UserIdOrEmail, UpdDate, AdminId, StateCode
                            ,Reason
                             )
                      VALUES(
                             v_intAdminNo, pi_strJoinAdminId, SYSDATE, pi_strAdminId, 100
                            ,'관리자 계정 생성'
                             );

        INSERT INTO TUserRoles(
                               User_Role_Num, UserId, Role
                               )
                        VALUES(
                               RoleNo_SEQ.NEXTVAL, pi_strJoinAdminId, 'ROLE_ADMIN'
                               );

        po_intRetVal := 0;
        po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_ACCOUNT_INS;