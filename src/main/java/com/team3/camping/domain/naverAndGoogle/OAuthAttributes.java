package com.team3.camping.domain.naverAndGoogle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Setter // 추가
@Slf4j // 추가
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    private String authVendor; // 추가

    @Builder // authVendor 추가
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
    					   String name, String email, String picture, String authVendor) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.authVendor = authVendor; // 추가
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {

    	OAuthAttributes result = null;

    	if("naver".equals(registrationId)) {
            result = ofNaver("id", attributes);
        } // 추가 (google)
    	else if("google".equals(registrationId)) {
            result = ofGoogle(userNameAttributeName, attributes);
    	}

    	log.info("registrationId : " + registrationId); // 추가

    	// 추가
    	result.setAuthVendor(registrationId);

    	return result;
	}

    // 추가 : 코드 활성화(생략 풀기)
    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

    	return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    @SuppressWarnings("unchecked")
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {

    	Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .authVendor(authVendor) // 추가
                .role(Role.USER)
                .build();
    }

}