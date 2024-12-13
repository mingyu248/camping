package com.team3.camping.config;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.team3.camping.service.CustomOAuth2UserService;
import com.team3.camping.service.CustomProviderService;

import groovy.util.logging.Log;

// spring & thymeleaf : 
// https://www.thymeleaf.org/doc/articles/springsecurity.html

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
    // 커스텀 인증 설정(DB 테이블명, 컬럼명, 예외처리 등...)을 하고, 해당 설정을 기반으로 처리하는 객체
    CustomProviderService customProviderService;
    // 사용자 이름을 기반으로 사용자의 정보를 가져오는데 사용되는 객체, 인증 과정에서도 사용된다
    public WebSecurityConfig(CustomProviderService customProviderService, CustomOAuth2UserService customOAuth2UserService) {
    	
         this.customProviderService = customProviderService;
         this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.authenticationProvider(customProviderService);

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));
        
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                                                             .requestMatchers("/login", "/login_fail", "/css/**", "/js/**", "/images/**"
                                                                             ,"/join", "/logout_proc", "/duplicate_check/*", "/page_access_denied" ,"/main_router"
                                                                             ,"/user_join", "/home", "/", "/user/**", "/oauth2/authorization/**"
                                                                             ,"/admin/camp/*", "/login_fail_social", "/error")
                                                             .permitAll()
                                                             .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                                                             .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER")
                                                             .anyRequest().authenticated());

        http.csrf((csrf)->csrf.disable()); // csrf 토큰 미사용

        // 로그인/로그아웃 (인증) 처리
        http.formLogin(formLogin -> formLogin.loginProcessingUrl("/login")            // 로그인 기능 동작 URL
                                             .loginPage("/account")                   // 로그인 페이지 URL 지정 (접속 불가 URL 등에 대해서 해당 URL로 리다이렉트)
                                             .usernameParameter("userId")             // 아이디
                                             .passwordParameter("userPwd")            // 패쓰워드
                                             .defaultSuccessUrl("/main", true)        // 로그인 성공시 이동 주소, 로그인 성공 후 무조건 해당 경로로 리다이렉트할지 여부(true로 설정)
                                             .failureUrl("/login_fail")               // 로그인 에러 처리
                                             // .failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러
                                             .permitAll())
            // logout 핸들링(처리)
            .logout((logout) -> logout.logoutSuccessUrl("/main") // 로그아웃 이후 이동 주소
                                      .permitAll()
                    );
        http.oauth2Login(oauth2LoginCustomizer ->
		  oauth2LoginCustomizer
		    .defaultSuccessUrl("/main", true) // 추가
		    .failureHandler((request, response, exception) -> {
	            response.sendRedirect("/camping/login_fail_social?errorMessage=" + URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8.toString()));
	        })
		  	.userInfoEndpoint(userInfoEndpointCustomizer ->
		  						userInfoEndpointCustomizer
		  							.userService(customOAuth2UserService)));
        
        // 예외처시 해당 url로 이동. ex 사용자 계정으로 관리자 페이지 접속
        http.exceptionHandling(handler -> handler.accessDeniedPage("/page_access_denied"));

        return http.build();
    }
}