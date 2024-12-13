--------------------------------------------------------------------------------------------------------
-- ProcedureName     : ADMIN_COMMENT_DEL
-- Description       : 후기 댓글 삭제(관리자)
-- Author            : KSW, 2024-11-30
--------------------------------------------------------------------------------------------------------
create or replace PROCEDURE ADMIN_COMMENT_DEL (
    pi_intCommentNo   IN  NUMBER,

    po_intRetVal      OUT NUMBER,
    po_strRetVal      OUT VARCHAR2
) AS
    v_intStateCode NUMBER;
BEGIN

    DELETE 
    FROM   TReviewComment
    WHERE  ReviewCommentNo = pi_intCommentNo;

       po_intRetVal := 0;
       po_strRetVal := '해당 댓글이 제거 되었습니다';

    COMMIT;

EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        po_intRetVal := 9999;
        po_strRetVal := SQLERRM; -- 오류 메시지 반환
END ADMIN_COMMENT_DEL;