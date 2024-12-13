--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_COMMENT_ACT
-- Description       : 후기 댓글 활성화(관리자)
-- Author            : KSW, 2024-11-30
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_COMMENT_ACT (
    pi_intCommentNo   IN  NUMBER,

    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN
    SELECT StateCode
    INTO   v_intStateCode
    FROM   TReviewComment
    WHERE  ReviewCommentNo = pi_intCommentNo;

    IF v_intStateCode = 100 THEN
        po_intRetVal := 1310;
        po_strRetVal := '이미 활성화중인 댓글 입니다.';
        RETURN;
    END IF;

        UPDATE TReviewComment
        SET    StateCode = 100
        WHERE  ReviewCommentNo = pi_intCommentNo;

       po_intRetVal := 0;
       po_strRetVal := '해당 댓글이 활성화 되었습니다';

    COMMIT;


EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_COMMENT_ACT;