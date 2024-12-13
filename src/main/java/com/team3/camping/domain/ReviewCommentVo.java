package com.team3.camping.domain;

import java.sql.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCommentVo {
    
    private long   reviewCommentNo;   // 후기 댓글 번호
    private int    reviewNo;          // 후기 번호
    private String userIdOrEmail;     // 댓글 작성자
    private String comments;          // 댓글 내용
    private Date   regDate;           // 댓글 등록일
    
    private Date   updDate;           // 댓글 수정일
    private String stateCode;         // 상태코드
    private int    reviewComentRowNo; // 댓글 행 번호
    private int    count;             // 갯수
}
