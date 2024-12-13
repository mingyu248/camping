--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_ACCOUNT_DEL
-- Description       : 관리자 계정 삭제
-- Author            : KSW, 2024-11-20
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE "ADMIN_ACCOUNT_DEL" 
(
    pi_strAdminId IN  VARCHAR2,
    
    po_intRetVal  OUT NUMBER,
    po_strRetVal  OUT VARCHAR2
) AS
BEGIN
    DELETE 
    FROM   TUserInfo    
    WHERE  UserId = pi_strAdminId;

    DELETE 
    FROM   TUserHis
    WHERE  UserIdOrEmail = pi_strAdminId;

    DELETE
    FROM   TUserRoles
    WHERE  UserId = pi_strAdminId;

    DELETE
    FROM   TCampReservation
    WHERE  UserIdOrEmail = pi_strAdminId;
    
    DELETE
    FROM   TCartHis
    WHERE  UserIdOrEmail = pi_strAdminId;
    
    DELETE
    FROM   TCampSuggestion
    WHERE  UserIdOrEmail = pi_strAdminId;
    
    DELETE
    FROM   THelp
    WHERE  UserIdOrEmail = pi_strAdminId;
    
    DELETE
    FROM   TReview
    WHERE  UserIdOrEmail = pi_strAdminId;

    DELETE
    FROM   TReviewComment
    WHERE  UserIdOrEmail = pi_strAdminId;
    
    po_intRetVal := 0;
    po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := SQLCODE;
        po_strRetVal := SQLERRM(SQLCODE);
END ADMIN_ACCOUNT_DEL;
