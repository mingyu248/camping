package com.team3.camping.repository.userRepository;

import com.team3.camping.domain.ResultInfo;
import com.team3.camping.domain.UserInfoVo;

public interface UserSocialAccountDao {

    // 소셜 정보 수정
    public ResultInfo socialAccountUpdDate(UserInfoVo objUserInfo);
}
