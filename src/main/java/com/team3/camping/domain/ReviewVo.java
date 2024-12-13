package com.team3.camping.domain;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewVo {
    
    private int           reviewNo;          // 후기번호
    private int           campNo;            // 캠핑장 번호
    private String        userIdOrEmail;     // 회원 아이디
    private String        reviewTitle;       // 후기 제목
    private String        reviewDetail;      // 후기 내용
    
    private String        reviewImgPath;     // 후기 이미지 프로젝트 경로
    private String        reviewImgRootPath; // 후기 이미지 절대경로
    private int           views;             // 후기 조회수
    private Date          regDate;           // 후기 등록일
    private int           reviewRowNo;       // 후기 행 번호
    
    private Date          updDate;           // 수정일
    private String        stateCode;         // 상태코드
    private String        reason;            // 사유
    private String        adminId;           // 관리자 아이디
    private int           count;             // 갯수
    
    private int 		  imgState;			 // 이미지 상태 (1: 변경 없음, 2: 변경, 3: 삭제)
    private MultipartFile file;              // 파일
    private String        pageInfo;          // 페이지 정보(캠핑장 페이지 or 마이페이지)
    
    private List<ReviewCommentVo> reviewCommentList; // 댓글
}


