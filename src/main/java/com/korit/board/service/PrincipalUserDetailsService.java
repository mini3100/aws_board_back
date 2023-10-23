package com.korit.board.service;

import com.korit.board.entity.User;
import com.korit.board.repository.UserMapper;
import com.korit.board.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PrincipalUserDetailsService implements UserDetailsService, OAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            throw new UsernameNotFoundException("UsernameNotFound");
        }

        return new PrincipalUser(user); // email을 찾았을 경우
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();    // 사용자 정보 로드하기 위한 서비스 생성
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);    // 사용자 정보 로드

        Map<String, Object> attributes = oAuth2User.getAttributes();    // 사용자 정보에서 속성 얻어옴.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");    // 속성에서 response 부분 꺼냄(id, email, name 등의 실질적 사용자 데이터)
        String provider = userRequest.getClientRegistration().getClientName();  // Naver, Kakao 등 provider 이름

        response.put("provider", provider); // provider 속성을 추가해줌.

        return new DefaultOAuth2User(new ArrayList<>(), response, "id");
    }
}
