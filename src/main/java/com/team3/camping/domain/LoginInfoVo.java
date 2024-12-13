package com.team3.camping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginInfoVo {

    private String userKey;       // userId or email, 사용자 판별 시 사용
    private String userName;      // userId or 소셜사용자 이름, 사용자의 이름을 띄우기 위한 이름
    private int    stateCode = 0; // 0: 로그아웃 상태, 1: 자체 로그인 상태, 2: 소셜 로그인 상태
}
