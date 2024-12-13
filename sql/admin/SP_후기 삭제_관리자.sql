--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_REVIEW_DEL
-- Description       : 후기 삭제(관리자)
-- Author            : KSW, 2024-11-22
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_REVIEW_DEL
(
    pi_intReviewNo IN  NUMBER,
    
    po_intRetVal   OUT NUMBER,
    po_strRetVal   OUT VARCHAR2
) AS
BEGIN
    DELETE 
    FROM   TReview    
    WHERE  ReviewNo = pi_intReviewNo;

    DELETE 
    FROM   TReviewHis
    WHERE  ReviewNo = pi_intReviewNo;

    DELETE
    FROM   TReviewComment
    WHERE  ReviewNo = pi_intReviewNo;

    po_intRetVal := 0;
    po_strRetVal := 'SUCCESS';
    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := SQLCODE;
        po_strRetVal := SQLERRM(SQLCODE);
END ADMIN_REVIEW_DEL;