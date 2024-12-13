package com.team3.camping.service.userService;

import java.util.List;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;

public interface UserAccountService {

    // 아이디 중복 체크
    public ResultInfo userDuplicateCheck(String strUserId);
    // 회원 정보 저장
    public ResultInfo userRegist(UserInfoVo objUser);
    // 유저 정보 가져오기(자체로그인)
    public UserInfoVo getUserInfo(String strUserKey);
    // 유저 정보 가져오기(소셜로그인)
    public UserInfoVo getSocialUserInfo(String userKey);
    // 회원정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    public ResultInfo userInfoUpdate(UserInfoVo objUser);

    
    // 패스워드 변경 시 기존 패스워드 체크
    public String getPwd(String string);
    // 탈퇴 사유 리스트 가져오기
    public List<SecReasonVo> getSecessionReasonList();
    // 회원 탈퇴
    public ResultInfo userSecession(SecReasonVo objSecReason);
}
