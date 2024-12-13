package com.team3.camping.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.team3.camping.domain.naverAndGoogle.OAuthAttributes;
import com.team3.camping.domain.naverAndGoogle.SessionUser;
import com.team3.camping.domain.naverAndGoogle.User;
import com.team3.camping.repository.LoginDao;
import com.team3.camping.repository.userRepository.UserRepository;

import jakarta.servlet.http.HttpSession;

//import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Getter // 추가
@Setter // 추가
@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private LoginDao userLoginDao;
    
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @SuppressWarnings({"rawtypes", "unchecked"})
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        System.out.println("=========[CustomOAuth2UserService][loadUser]=====");

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId        = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                                  .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        user.setAuthVendor(attributes.getAuthVendor());

        log.info("CustomOAuth2UserService loadUser user : " + user);
        
        // 상태코드 검사
        int intStateCode = 0;
        
        try { 
            intStateCode = userLoginDao.loadSocialUserStateCode(user.getEmail());
            
            if (intStateCode == 100) {
            	httpSession.setAttribute("user", new SessionUser(user));

            } else if (intStateCode == 900) {
                log.info("정지된 아이디로 접속 시도 (유저:" + user.getEmail() + ")");
                throw new DisabledException("정지된 아이디입니다.");
                
            } else if (intStateCode == 999) {
                log.info("탈퇴된 아이디로 접속 시도 (유저:" + user.getEmail() + ")");
                throw new DisabledException("탈퇴된 아이디입니다.");
                
            } else if (intStateCode == -1) {
                log.info("탈퇴된 아이디로 접속 시도 (유저:" + user.getEmail() + ")");
                throw new DisabledException("사용자의 상태코드 판별 중 에러");
                
            } else {
                log.info("사용 불가 아이디로 접속 시도 (유저:" + user.getEmail() + ")");
                throw new DisabledException("사용할 수 없는 아이디입니다");
            }
        } catch(DisabledException disabledEx) {
            log.info(user.getEmail() + " 사용자의 상태코드 : " + intStateCode);
            throw new DisabledException(disabledEx.getMessage());
        }
        return new DefaultOAuth2User(
					                 Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
					                 attributes.getAttributes(),
					                 attributes.getNameAttributeKey()
					                 );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {

    	log.info("회원정보 저장/수정");

        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(),
                							 attributes.getPicture(),
                							 attributes.getAuthVendor())) // attributes.getAuthVendor() 추가
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}