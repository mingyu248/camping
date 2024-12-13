package com.team3.camping.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionVo {
    
    private int    campNo;  // 캠핑장 번호
    private String userKey; // 유저키
}
