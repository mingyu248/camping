package com.team3.camping.repository.userRepository;

import java.util.List;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.SecReasonVo;
import com.team3.camping.domain.UserInfoVo;

public interface UserAccountDao {
    
    // 아이디 중복 체크
    public ResultInfo userDuplicateCheck(String strUuserId);
    // 회원 정보 저장
    public ResultInfo userInfoRegist(UserInfoVo objUser);
    // 회원 기록 저장
    public ResultInfo userHisRegist(UserInfoVo objUser);
    // 회원 권한 저장
    public ResultInfo userRoleRegist(UserInfoVo objUser);
    // 회원정보 가져오기(자체로그인)
    public UserInfoVo getUserInfo(String strUserKey);

    
    // 회원정보 가져오기(소셜로그인)
    public UserInfoVo getSocialUserInfo(String strUserKey);
    // 회원정보 수정(이메일, 비밀번호, 주소, 생년월일, 성별, 이름, 전화번호)
    public ResultInfo userInfoUpdate(UserInfoVo objUser);
    // 기존 비밀번호 가져오기
    public String getPwd(String strUserKey);
    // 탈퇴 사유 리스트 가져오기
    public List<SecReasonVo> getSecessionReasonList();
    // 회원 탈퇴
    public Integer userSecession(SecReasonVo objSecReason);

    // 회원 탈퇴 User_Tbl 상태코드 추가
    public Integer SetUserTblStateCode(SecReasonVo objSecReason);
}
