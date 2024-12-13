--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_COMMENT_STOP
-- Description       : 후기 댓글 정지(관리자)
-- Author            : KSW, 2024-11-30
--------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ADMIN_COMMENT_STOP (
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

    IF v_intStateCode = 900 THEN
        po_intRetVal := 1310;
        po_strRetVal := '이미 정지된 댓글 입니다.';
        RETURN;
    END IF;

        UPDATE TReviewComment
        SET    StateCode = 900
        WHERE  ReviewCommentNo = pi_intCommentNo;

       po_intRetVal := 0;
       po_strRetVal := '해당 댓글이 정지 되었습니다';

    COMMIT;


EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_COMMENT_STOP;