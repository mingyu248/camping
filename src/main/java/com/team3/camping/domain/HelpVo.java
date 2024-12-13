package com.team3.camping.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelpVo {
    
    private int    helpNo;        // 문의 번호
    private String userIdOrEmail; // 문의 작성자
    private String helpTitle;     // 문의 제목
    private String helpDetail;    // 문의 내용
    private Date   regDate;       // 문의 등록일
    
    private String adminId;       // 관리자 아이디
    private String answer;        // 문의 답변 내용
    private Date   answerDate;    // 답변일
    private String stateCode;     // 상태코드
    private int    helpRowNo;     // 행 번호
    
    private String regWeek;
    private String regMonth;
    private int    count;         // 갯수
}
